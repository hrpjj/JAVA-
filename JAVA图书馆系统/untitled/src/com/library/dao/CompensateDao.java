package com.library.dao;

import com.library.entity.Compensate;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompensateDao {

    // 新增赔偿记录
    public boolean addCompensate(Compensate compensate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO compensate(user_id,book_id,cpt_cause,cpt_result,operator_id) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, compensate.getUserId());
            pstmt.setString(2, compensate.getBookId());
            pstmt.setString(3, compensate.getCptCause());
            pstmt.setBigDecimal(4, compensate.getCptResult());
            pstmt.setInt(5, compensate.getOperatorId());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 查询所有赔偿记录
    public List<Compensate> getAllCompensates() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Compensate> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM compensate ORDER BY cpt_time DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Compensate cpt = new Compensate();
                cpt.setCptId(rs.getInt("cpt_id"));
                cpt.setUserId(rs.getInt("user_id"));
                cpt.setBookId(rs.getString("book_id"));
                cpt.setCptCause(rs.getString("cpt_cause"));
                cpt.setCptResult(rs.getBigDecimal("cpt_result"));
                cpt.setCptTime(rs.getTimestamp("cpt_time"));
                cpt.setOperatorId(rs.getInt("operator_id"));
                list.add(cpt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}