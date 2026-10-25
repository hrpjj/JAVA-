package com.library.dao;

import com.library.entity.BookEnter;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookEnterDao {

    // 新增入库记录
    public boolean addEnter(BookEnter enter) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO book_enter(book_id,enter_qty,operator_id) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, enter.getBookId());
            pstmt.setInt(2, enter.getEnterQty());
            pstmt.setInt(3, enter.getOperatorId());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 查询所有入库记录
    public List<BookEnter> getAllEnters() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookEnter> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM book_enter ORDER BY enter_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BookEnter enter = new BookEnter();
                enter.setEnterId(rs.getInt("enter_id"));
                enter.setBookId(rs.getString("book_id"));
                enter.setEnterQty(rs.getInt("enter_qty"));
                enter.setEnterTime(rs.getTimestamp("enter_time"));
                enter.setOperatorId(rs.getInt("operator_id"));
                list.add(enter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}