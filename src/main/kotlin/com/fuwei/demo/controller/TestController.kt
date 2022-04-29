package com.fuwei.demo.controller

import com.fuwei.demo.base.BaseContentType
import com.fuwei.demo.base.BaseResponse
import com.fuwei.demo.data.FileData
import com.fuwei.demo.data.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/get")
    fun testGet(): BaseResponse {

        return BaseResponse(200, BaseContentType.JSON_OBJECT.ordinal, User("fuwei", 35, "male"))
    }

    @PostMapping("/post/upload")
    @ResponseBody
    fun testPostUpload(
        @RequestParam("file") multipartFile: MultipartFile,
        httpServletRequest: HttpServletRequest
    ): BaseResponse {
        var response = BaseResponse(200, BaseContentType.TEXT.ordinal, "未知")
        if (multipartFile.isEmpty) {
            response.code = 201
            response.content = "上传文件为空"
        } else {
            val size = multipartFile.size
            val originFileName = multipartFile.originalFilename
            val contentType = multipartFile.contentType
            val dir = httpServletRequest.getParameter("dir")

            var fileDir = File(System.getProperty("user.dir") + File.separator + dir)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            val file = File(fileDir.path + File.separator + originFileName)
            multipartFile.transferTo(file)
            println("transfer file path = ${file.path} , name = ${file.name}")

            val desc = "upload file size = $size, fileName = $originFileName ,contentType = $contentType ,dir = $dir"
            response.content = desc
            println(desc)
        }
        return response
    }

    @GetMapping("/get/file/list")
    fun testPostFileList(@RequestParam("dir") dir: String): BaseResponse {
        val response = BaseResponse(200, BaseContentType.TEXT.ordinal, "")
        var fileDir = File(System.getProperty("user.dir") + File.separator + dir)
        if (fileDir.exists()) {
            val files = fileDir.listFiles()
            if (files != null) {
                val list = ArrayList<FileData>()
                for (file in files) {
                    val url = "http://106.12.132.116:8088/test/get/file/download?path=${file.path}"
                    list.add(
                        FileData(
                            file.name,
                            file.path,
                            url,
                            file.length(),
                            file.isDirectory,
                            time2Rfc3339(file.lastModified())
                        ).also {
                            println("modTime = ${it.modTime}")
                        }
                    )
                }
                response.contentType = BaseContentType.JSON_ARRAY.ordinal
                response.content = list
            } else {
                response.content = "文件路径【dir=${dir}】中不存在文件"
            }
        } else {
            response.content = "不存在文件路径【dir=${dir}】"
        }
        return response
    }


    fun time2Rfc3339(timeStamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return simpleDateFormat.format(Date(timeStamp)) + "Z"
    }

    @GetMapping("/get/file/download")
    fun testGetDownloadFile(@RequestParam("path") filePath: String, response: HttpServletResponse): String {

        val path = filePath.replace("\"".toRegex(), "")
        val file = File(path)

        println("path = $path ,filePath = $filePath")
        if (!file.exists()) {
            return "download failed,file not exist."
        }
        response.reset()
        response.contentType = "application/octet-stream"
        response.characterEncoding = "UTF-8"
        response.setContentLength(file.length().toInt())
        response.setHeader("Content-Disposition", "attachment;fileName=${URLEncoder.encode(file.name, "UTF-8")}")

        val bis = BufferedInputStream(FileInputStream(file))
        val buffer = ByteArray(1024)
        val os = response.outputStream
        var i = 0
        while (bis.read(buffer).also { i = it } != -1) {
            os.write(buffer, 0, i)
            os.flush()
        }
        bis.close()
        os.close()
        return "download success"
    }
}