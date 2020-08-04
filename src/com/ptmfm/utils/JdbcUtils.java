package com.ptmfm.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;

    private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    static {


        try {
            Properties properties = new Properties();
//            读取jdbc.properties属性配置文件
            InputStream resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
//           从流中读取数据
            properties.load(resourceAsStream);
//            创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    * 获取数据库连接池中的连接
    *如果返回null,说明获取连接失败，有值表示成功
    */
    public static Connection getConnection(){
        Connection conn = conns.get();
        if(conn == null){
            try {
                conn = dataSource.getConnection();//从数据可连接池中获取连接
                conns.set(conn); //保存到ThreadLocal对象中，工JDBC使用
                conn.setAutoCommit(false); //设置手动管理事物
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;

    }


    /*提交事务，并关闭连接*/
    public static void commitAndClose(){
        Connection connection = conns.get();
        if(connection != null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.commit();//提交事务

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要remove，因为Tomcat服务器底层使用了线程池
        conns.remove();
    }
    /*回滚事务，并关闭连接*/
    public static void rollbackAndClose(){

        Connection connection = conns.get();
        if(connection != null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.rollback();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要remove，因为Tomcat服务器底层使用了线程池
        conns.remove();
    }

   /* *//*
    * 关闭连接,放回数据库连接池
    *//*
    public static void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
}
