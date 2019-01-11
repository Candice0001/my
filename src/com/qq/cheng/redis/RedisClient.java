package com.qq.cheng.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.Scanner;

/**
 * 
 * Title: RedisClient.java
 * Description: 
 * @author chengge
 * @date 2018年6月5日
 *
 */
public class RedisClient {
	
	static Socket socket=null;
	static OutputStream os=null;
	static InputStream in=null;
	static final int PORT=6379;
	
	public static void main(String[] args) {
		//sendInfo_client();
		//testServer();
		//testRedisSet();
		//testRedisGet();
		
	}
	
	public static void sendInfo_client() {
		//定义redis服务端默认端口
		int port=6379;
		Socket socket = null;
		BufferedReader reader = null;
		PrintWriter writer = null;
		
		try {
			//创建TCP连接
			socket=new Socket("127.0.0.1",port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(),true);
			
			//传送info命令
			//客户端向redis服务器发送命令，以redis整块字符串数组的形式
			writer.println("*1\r\n$4\r\ninfo\r\n");
			System.out.println("Redis command wat sent successfully.");
			
			//接收服务器的回复
			CharBuffer response = CharBuffer.allocate(1024);
			int readedlen = reader.read(response);
			String responseBody = response.flip().toString();
			
			//输出服务器的回复
			System.out.println(responseBody);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				//最后关闭相关的流
				if (writer !=null) {
					writer.close();
				}
				if (reader!=null) {
					reader.close();
				}
				if (socket!=null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * PING PONG :
	 * Title: testServer
	 * Description:   登录redis cli客户端后, 输入ping, 服务器会返回pong,
	 * 					 来表示连接状况是完好的, 也表示了服务器大体上是正常运转的.
	 * @time 2018年6月5日 上午11:32:53
	 */
	public static void testServer() {
		
		try {
			socket = new Socket("127.0.0.1", PORT);
			os=socket.getOutputStream();
			in=socket.getInputStream();
			//向redis服务器写
			os.write("PING\r\n".getBytes());
			
			//从redis服务器读到bytes中
			byte[] bytes=new byte[1024];
//			int len = in.read(bytes);
//			System.out.println(new String(bytes,0,len)); //+PONG
			if (in.read()=='+') {
				int len=in.read(bytes);
				System.out.println(new String(bytes,0,len));
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	/**
	 * 实现redis的set方法
	 * Title: testRedisSet
	 * Description:  
	 * @time 2018年6月5日 下午2:33:10
	 */
	public static void testRedisSet() {
		try {
			socket=new Socket("127.0.0.1", PORT);
			os=socket.getOutputStream();
			in=socket.getInputStream();
			//向redis服务器写
			os.write("set hello 你好123\r\n".getBytes());
			
			//从redis服务器读
			byte[] bytes=new byte[1024];
			int len=in.read(bytes);
			//输出
			System.out.println(new String(bytes,0,len));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
	
	public static void testRedisGet() {
		try {
			socket=new Socket("127.0.0.1", PORT);
			os=socket.getOutputStream();
			in=socket.getInputStream();
			//向redis服务器写
			os.write("get hello\r\n".getBytes());
			//读
			byte[] bytes=new byte[1024];
			int len=in.read(bytes);
			System.out.println(new String(bytes,0,len));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 关闭IO，socket
	 */
	public static String close() {
		try {
			if (os!=null) {
				os.close();
			}
			if (in!=null) {
				in.close();
			}
			if (socket!=null) {
				socket.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return "close异常";
		}
		return "CLOSE OK";
	}
	
	public static Socket getConn() {
		try {
			socket=new Socket("127.0.0.1", PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	
	
}
