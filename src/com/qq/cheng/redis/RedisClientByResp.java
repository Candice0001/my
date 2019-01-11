package com.qq.cheng.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class RedisClientByResp {
	
	 private Socket socket;

	    public RedisClientByResp() {
	        try {
	            socket = new Socket("127.0.0.1", 6379);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("连接失败" + e.getMessage());
	        }
	    }

	    /**
	     * 设置值
	     * @param key
	     * @param value
	     * @return
	     * @throws IOException
	     */
	    public String set(String key, String value) throws IOException {
	        StringBuilder sb = new StringBuilder();
	        sb.append("*3").append("\r\n");
	        sb.append("$").append(CommandRedis.SET.name().length()).append("\r\n");
	        sb.append(CommandRedis.SET.name()).append("\r\n");
	        // 注意中文汉字。一个汉字两个字节，长度为2
	        sb.append("$").append(key.getBytes().length).append("\r\n");
	        sb.append(key).append("\r\n");
	        sb.append("$").append(value.getBytes().length).append("\r\n");
	        sb.append(value).append("\r\n");
	        System.out.println(sb.toString());

	        socket.getOutputStream().write(sb.toString().getBytes());
	        byte[] b = new byte[2048];
	        socket.getInputStream().read(b);
	        return new String(b);
	    }

	    /**
	     * 获取值
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public String get(String key) throws Exception {
	        StringBuffer stringBuffer = new StringBuffer();
	        stringBuffer.append("*2").append("\r\n");
	        stringBuffer.append("$").append(CommandRedis.GET.name().length()).append("\r\n");
	        stringBuffer.append(CommandRedis.GET).append("\r\n");
	        stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
	        stringBuffer.append(key).append("\r\n");

	        socket.getOutputStream().write(stringBuffer.toString().getBytes());
	        byte[] b = new byte[2048];
	        socket.getInputStream().read(b);
	        return new String(b);
	    }

	    /**
	     * 设置值：不会覆盖存在的值
	     * @param key
	     * @param value
	     * @return
	     * @throws Exception
	     */
	    public String setnx(String key, String value) throws Exception {
	        StringBuffer stringBuffer = new StringBuffer();
	        stringBuffer.append("*3").append("\r\n");
	        stringBuffer.append("$").append(CommandRedis.SETNX.name().length()).append("\r\n");
	        stringBuffer.append(CommandRedis.SETNX.name()).append("\r\n");
	        stringBuffer.append("$").append(key.getBytes().length).append("\r\n");
	        stringBuffer.append(key).append("\r\n");
	        stringBuffer.append("$").append(value.getBytes().length).append("\r\n");
	        stringBuffer.append(value).append("\r\n");

	        socket.getOutputStream().write(stringBuffer.toString().getBytes());
	        byte[] b = new byte[2048];
	        socket.getInputStream().read(b);
	        return new String(b);
	    }

	    public static void main(String[] args) throws Exception {
	        System.out.println(new RedisClientByResp().set("mykey" ,"myvalue"));
	        System.out.println(new RedisClientByResp().get("mykey"));
	    	
	    }

}
