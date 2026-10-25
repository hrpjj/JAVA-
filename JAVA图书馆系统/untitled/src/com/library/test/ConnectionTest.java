package com.library.test;

import com.library.utils.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionTest {
    public static void main(String[] args) {
        // 1. 获取数据库连接
        Connection conn = DBUtil.getConnection();

        if (conn != null) {
            System.out.println("✅ 数据库连接成功！");

            // 2. 执行一句简单SQL，验证能正常操作数据库
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT VERSION()");
                if (rs.next()) {
                    System.out.println("MySQL版本：" + rs.getString(1));
                }
                // 关闭资源
                DBUtil.close(conn, stmt, rs);
                System.out.println("✅ SQL执行测试通过，连接完全正常！");
            } catch (Exception e) {
                System.out.println("❌ 连接成功，但SQL执行出错");
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ 数据库连接失败，请检查下方报错信息");
        }
    }
}