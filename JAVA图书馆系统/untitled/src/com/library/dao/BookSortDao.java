package com.library.dao;

import com.library.entity.BookSort;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookSortDao {

    // 查询所有图书类别
    public List<BookSort> getAllSorts() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BookSort> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM book_sort";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BookSort sort = new BookSort();
                sort.setBookSort(rs.getString("book_sort"));
                sort.setSortDesc(rs.getString("sort_desc"));
                list.add(sort);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}