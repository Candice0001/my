package com.qq.cheng.io;

/* 使用FileOutputStream写入文件，FileOutputStream的write() 方法只接受byte[] 类型
的参数，所以需要将string通过getBytes()方法转换为字节数组。
1、首先判断文件是否存在，不存在就新建一个
2、写入文件是以覆盖方式
3、文件不存在会自动创建，存在则会被重写
 */

import java.io.*;

public class Exercise {
  
  public static void main(String args[]) {
    FileOutputStream fos = null;
    File file;
    String mycontent = "This is my Data which needs to be written into the file.";
    try {
      // specify the file path
      file = new File("/home/zjz/Desktop/myFile.txt");
      fos = new FileOutputStream(file);
      /* This logic will check whether the file exists or not.
      if the file is not found at the specified location it would create
      a new file
       */
//      if (!file.exists()) {
//        file.createNewFile();
//      }
      /* String content cannot be directly written into a file.
      It needs to be converted into bytes
       */
      byte[] bytesArray = mycontent.getBytes();
      fos.write(bytesArray);
      fos.flush();//刷新缓存区
      System.out.println("File Written Successfully");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (fos != null) {
          fos.close();
        }
      } catch (IOException ioe) {
        System.out.println("Error in closing the Stream");
      }
    }
  }

}
