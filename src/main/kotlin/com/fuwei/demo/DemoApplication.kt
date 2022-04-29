package com.fuwei.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)


    val userDir = System.getProperty("user.dir")
    println("user.dir = ${System.getProperty("user.dir")}")

    val fileName = "test.txt"
    val file = File(userDir + File.separator + fileName)
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText("My name is jiangfuwei.")

    println("create file name = ${file.name} ,path = ${file.path}")
}
