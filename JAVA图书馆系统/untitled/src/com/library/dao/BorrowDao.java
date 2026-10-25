package com.library.dao;

import com.library.entity.Borrow;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BorrowDao {

    // 新增借阅记录
    public boolean addBorrow(Borrow borrow) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO borrow(user_id,book_id,borrow_qty) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, borrow.getUserId());
            pstmt.setString(2, borrow.getBookId());
            pstmt.setInt(3, borrow.getBorrowQty());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 归还图书：更新归还时间
    public boolean returnBook(int borrowId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE borrow SET return_time = NOW() WHERE borrow_id = ? AND return_time IS NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, borrowId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 查询用户未归还的借阅记录
    public List<Borrow> getUnreturnedByUserId(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM borrow WHERE user_id = ? AND return_time IS NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setBorrowId(rs.getInt("borrow_id"));
                borrow.setUserId(rs.getInt("user_id"));
                borrow.setBookId(rs.getString("book_id"));
                borrow.setBorrowTime(rs.getTimestamp("borrow_time"));
                borrow.setBorrowQty(rs.getInt("borrow_qty"));
                borrow.setReturnTime(rs.getTimestamp("return_time"));
                list.add(borrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 查询所有借阅记录
    public List<Borrow> getAllBorrows() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Borrow> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM borrow ORDER BY borrow_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setBorrowId(rs.getInt("borrow_id"));
                borrow.setUserId(rs.getInt("user_id"));
                borrow.setBookId(rs.getString("book_id"));
                borrow.setBorrowTime(rs.getTimestamp("borrow_time"));
                borrow.setBorrowQty(rs.getInt("borrow_qty"));
                borrow.setReturnTime(rs.getTimestamp("return_time"));
                list.add(borrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}