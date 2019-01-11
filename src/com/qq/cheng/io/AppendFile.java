package com.qq.cheng.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chengge
 * 
 * 
 * 字节流：每次读入或输出的是8位二进制。字符流：每次读入或输出的是16位二进制，即两个字节。
 * 字节流输入流：InputStream 字节输出流：OutputStream
 * 字符输入流：Reader,字符输出流：Writer
 */
public class AppendFile {
	
	/**
	 * BufferedWriter,以追加形式写文件
	 * 使用了缓存器，将缓存写满后（或者close以后）才输出到文件
	 * 将文本写入字符输出流，缓冲各个字符，从而提供单个字符、数组和字符串的高效写入。
	 * 可以指定缓冲区的大小，或者接受默认的大小 BufferedWriter(Writer out, int sz) 
	 * @param file
	 * @param conent
	 */
	public static void method1(String file, String conent) {   
        BufferedWriter out = null;   
        try {   
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));   
            /*for (int x = 0; x < 10; x++) {
				out.write(x+" ");
			}*/
            out.write(conent); 
            out.write("\n");
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
            	if(out != null){
            		out.close();   
                }
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }   
  
    /**  
     * 追加文件：使用FileWriter  
     * 继承自OutputStreamWriter
     * 每写一次数据，磁盘就会进行一次写操作，性能较差  
     * @param fileName  
     * @param content  
     */  
    public static void method2(String fileName, String content) { 
    	FileWriter writer = null;
        try {   
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
            writer = new FileWriter(fileName, true);   
            /*for (int x = 0; x < 10; x++) {
				writer.write(x+" ");
			}*/
            writer.write(content); 
            writer.write("\n");
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
            	if(writer != null){
            		writer.close();   
            	}
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        } 
    }   
  
    /**  
     * 追加文件：使用RandomAccessFile  
     *   
     * @param fileName 文件名  
     * @param content 追加的内容  
     */  
    public static void method3(String fileName, String content) { 
    	RandomAccessFile randomFile = null;
        try {   
            // 打开一个随机访问文件流，按读写方式   
            randomFile = new RandomAccessFile(fileName, "rw");   
            // 文件长度，字节数   
            long fileLength = randomFile.length();   
            // 将写文件指针移到文件尾。   
            randomFile.seek(fileLength);   
            randomFile.writeBytes(content);    
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally{
        	if(randomFile != null){
        		try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
    }  
    
    /**
     * 
     * @param filename 文件名
     * @param content 写入的内容
     */
    public static void method4(String filename,String content) {
    	// 创建字节输出流对象
		// FileOutputStream fos = new FileOutputStream("fos3.txt");
		// 创建一个向具有指定name的文件中写入数据的输出文件流。如果第二个参数为true,则将字节写入文件末尾处，而不是写入文件开始处
		FileOutputStream fos2=null;
		try {
			fos2 = new FileOutputStream(filename, true);// 第二個参数为true表示程序每次运行都是追加字符串在原有的字符上
			// 写数据
			/*for (int x = 0; x < 10; x++) {
				fos2.write((x+" ").getBytes());
				fos2.write("\r\n".getBytes());// 写入一个换行
			}*/
			fos2.write(content.getBytes());
			fos2.write("\n".getBytes());//写入换行
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (null!=fos2) {
					// 释放资源
					fos2.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
    }
    
    public static String createFileName() {
    	String filename="";
    	Date now=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	filename=sdf.format(now);
    	return filename;
    }

	public static void main(String[] args) {
		/*try{
			File file = new File("d://text4.log");
			if(file.createNewFile()){
				System.out.println("Create file successed");
			}else {
				if(file.exists()) {
					System.out.println("File exists...");
				}
			}
			String contents="{\"json\":\"json string\",\"json1\":\"json string1\",\"json2\":\"json string2\",\"json3\":\"json string3\",\"json4\":\"json string4\"}";
			String contents2="";
			if(contents=="" || contents==null) {
				contents="没有数据";
			}
			long startTime=System.currentTimeMillis();
//			method1("d://text1.log", contents);
			method2("d://text2.log", contents);
//			method3("d://text.txt", "123");
			method4("d://text4.log", contents);
			long endTime=System.currentTimeMillis();
			System.out.println(endTime-startTime);
		}catch(Exception e){
			System.out.println(e);
		}*/
		System.out.println(createFileName());
	}
}

