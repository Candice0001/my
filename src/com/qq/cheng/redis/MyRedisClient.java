package com.qq.cheng.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 自己实现的可以与redis交互的客户端
 * Title: MyRedisClient.java
 * Description: 
 * @author chengge
 * @date 2018年6月6日
 *
 */
public class MyRedisClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 6379);
    	InputStream in=socket.getInputStream();
    	OutputStream os=socket.getOutputStream();
    	
    	Scanner scan = new Scanner(System.in);
        // 从键盘接收数据
 
        // nextLine方式接收字符串
        System.out.print("please input >>");
        // 判断是否还有输入
        while (scan.hasNextLine()) {
            String str2 = scan.nextLine();
            //System.out.println("输入的数据为：" + str2);
            //写
            os.write((str2+"\r\n").getBytes());
            //读
            byte[] bytes=new byte[1024];
            int len=in.read(bytes);
            System.out.println("redis server response >>"+new String(bytes,0,len));
            
            System.out.print("please input >>");
        }
    		
	}
}
