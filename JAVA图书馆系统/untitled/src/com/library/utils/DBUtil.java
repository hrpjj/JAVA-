package com.library.utils;

import java.sql.*;

/**
 * 数据库连接工具类
 * 封装数据库连接、关闭资源的通用方法
 */
public class DBUtil {
    // 数据库连接地址：library_management 是你建的数据库名，保持一致即可
    private static final String URL = "jdbc:mysql://localhost:3306/library_management?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    // 数据库用户名，默认一般是root
    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    /**
     * 获取数据库连接对象
     * @return Connection连接对象，连接失败返回null
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 加载MySQL 8.0驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC驱动加载失败，请检查驱动包是否导入");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ 数据库连接失败，请检查账号密码、数据库名是否正确");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭资源（查询操作使用，包含结果集）
     * @param conn 连接对象
     * @param stmt 执行对象
     * @param rs 结果集对象
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源（增删改操作使用，无结果集）
     * @param conn 连接对象
     * @param stmt 执行对象
     */
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}