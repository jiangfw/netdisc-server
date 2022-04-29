package com.fuwei.demo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    void test() throws IOException {
        File file = new File("aa");
        file.getPath().replaceAll("\"","");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[1024];
        int i = 0;
        while ((i = bis.read(buffer)) != -1) {

        }
    }


     String time(long timeStamp){
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
         return simpleDateFormat.format(new Date(timeStamp));
    }
}
