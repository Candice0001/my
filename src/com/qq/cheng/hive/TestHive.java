package com.qq.cheng.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestHive {
    //hive的jdbc驱动类
    public static String dirverName = "org.apache.hive.jdbc.HiveDriver"; 
    //连接hive的URL hive1.2.1版本需要的是jdbc:hive2，而不是 jdbc:hive 
    public static String url = "jdbc:hive2://192.168.41.135:10000/db_hive_edu";
    //登录linux的用户名  一般会给权限大一点的用户，否则无法进行事务形操作
    public static String user = "hadoop";
    //登录linux的密码
    public static String pass = "hadoop";
    /**
     * 创建连接
     * @return
     * @throws SQLException
     */
    public static Connection getConn(){
           Connection conn = null;
           try {
                  Class.forName(dirverName);
                  conn = DriverManager.getConnection(url, user, pass);
                  System.out.println("connection ok");
           } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
           return conn;
    }
    
    /**
     * 创建命令
     * @param conn
     * @return
     * @throws SQLException
     */
    public static Statement getStmt(Connection conn) throws SQLException{
          // logger.debug(conn);
           if(conn == null){
                  System.out.println("this conn is null");
           }
           return conn.createStatement();
    }
    
    /**
     * 关闭连接
     * @param conn
     */
    public static void closeConn(Connection conn){
           try {
                  conn.close();
           } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
    }
    
    /**
     * 关闭命令
     * @param stmt
     */
    public static void closeStmt(Statement stmt){
           try {
                  stmt.close();
           } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
    }
    
    public static void main(String[] args) {
    	System.out.println("starting...");
		Connection connection = getConn();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery("select id , stuno ,stuname ,sex from student");
			while(rs.next()){
				System.out.println(rs.getInt(1) + "," + rs.getString(2)+ "," + rs.getString(3)+ "," + rs.getString(4)) ;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				st.close();
				closeConn(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("ending...");
	}
}
