package com.library.dao;

import com.library.entity.Book;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    // 添加图书
    public boolean addBook(Book book) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO books(book_id,book_name,book_author,book_society,book_time,book_price,book_sort,book_qty,book_qtyx) VALUES(?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getBookId());
            pstmt.setString(2, book.getBookName());
            pstmt.setString(3, book.getBookAuthor());
            pstmt.setString(4, book.getBookSociety());
            pstmt.setDate(5, new java.sql.Date(book.getBookTime().getTime()));
            pstmt.setBigDecimal(6, book.getBookPrice());
            pstmt.setString(7, book.getBookSort());
            pstmt.setInt(8, book.getBookQty());
            pstmt.setInt(9, book.getBookQtyx());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 根据ISBN删除图书
    public boolean deleteBookById(String bookId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM books WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 修改图书信息
    public boolean updateBook(Book book) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE books SET book_name=?,book_author=?,book_society=?,book_time=?,book_price=?,book_sort=?,book_qty=?,book_qtyx=? WHERE book_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getBookName());
            pstmt.setString(2, book.getBookAuthor());
            pstmt.setString(3, book.getBookSociety());
            pstmt.setDate(4, new java.sql.Date(book.getBookTime().getTime()));
            pstmt.setBigDecimal(5, book.getBookPrice());
            pstmt.setString(6, book.getBookSort());
            pstmt.setInt(7, book.getBookQty());
            pstmt.setInt(8, book.getBookQtyx());
            pstmt.setString(9, book.getBookId());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 根据ISBN查询图书
    public Book getBookById(String bookId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Book book = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM books WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookSociety(rs.getString("book_society"));
                book.setBookTime(rs.getDate("book_time"));
                book.setBookPrice(rs.getBigDecimal("book_price"));
                book.setBookSort(rs.getString("book_sort"));
                book.setBookQty(rs.getInt("book_qty"));
                book.setBookQtyx(rs.getInt("book_qtyx"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return book;
    }

    // 查询所有图书
    public List<Book> getAllBooks() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Book> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM books";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookSociety(rs.getString("book_society"));
                book.setBookTime(rs.getDate("book_time"));
                book.setBookPrice(rs.getBigDecimal("book_price"));
                book.setBookSort(rs.getString("book_sort"));
                book.setBookQty(rs.getInt("book_qty"));
                book.setBookQtyx(rs.getInt("book_qtyx"));
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    // 图书借出：现存量减1
    public boolean decreaseStock(String bookId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE books SET book_qtyx = book_qtyx - 1 WHERE book_id = ? AND book_qtyx > 0";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 图书归还：现存量加1
    public boolean increaseStock(String bookId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE books SET book_qtyx = book_qtyx + 1 WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 图书入库：总库存 + 现存量 同时增加
    public boolean addStock(String bookId, int qty) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE books SET book_qty = book_qty + ?, book_qtyx = book_qtyx + ? WHERE book_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, qty);
            pstmt.setInt(2, qty);
            pstmt.setString(3, bookId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 图书出库：总库存 + 现存量 同时减少（库存不足时失败）
    public boolean reduceStock(String bookId, int qty) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE books SET book_qty = book_qty - ?, book_qtyx = book_qtyx - ? WHERE book_id = ? AND book_qty >= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, qty);
            pstmt.setInt(2, qty);
            pstmt.setString(3, bookId);
            pstmt.setInt(4, qty);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }
}

