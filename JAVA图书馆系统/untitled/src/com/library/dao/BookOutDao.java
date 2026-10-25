package com.library.dao;

import com.library.entity.BookOut;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookOutDao {

    // 新增出库记录
    public boolean addOut(BookOut out) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO book_out(book_id,out_qty,operator_id) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, out.getBookId());
            pstmt.setInt(2, out.getOutQty());
            pstmt.setInt(3, out.getOperatorId());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 查询所有出库记录
    public List<BookOut> getAllOuts() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookOut> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM book_out ORDER BY out_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BookOut out = new BookOut();
                out.setOutId(rs.getInt("out_id"));
                out.setBookId(rs.getString("book_id"));
                out.setOutQty(rs.getInt("out_qty"));
                out.setOutTime(rs.getTimestamp("out_time"));
                out.setOperatorId(rs.getInt("operator_id"));
                list.add(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}