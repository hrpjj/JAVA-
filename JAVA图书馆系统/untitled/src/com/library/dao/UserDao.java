package com.library.dao;

import com.library.entity.User;
import com.library.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // 登录验证
    public User login(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users WHERE user_name = ? AND user_pwd = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserSex(rs.getString("user_sex"));
                user.setUserPwd(rs.getString("user_pwd"));
                user.setUserTel(rs.getString("user_tel"));
                user.setUserClass(rs.getString("user_class"));
                user.setUserTime(rs.getTimestamp("user_time"));
                user.setLimitId(rs.getString("limit_id"));
                user.setUserState(rs.getString("user_state"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return user;
    }

    // 添加用户
    public boolean addUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO users(user_name,user_sex,user_pwd,user_tel,user_class,limit_id,user_state) VALUES(?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserSex());
            pstmt.setString(3, user.getUserPwd());
            pstmt.setString(4, user.getUserTel());
            pstmt.setString(5, user.getUserClass());
            pstmt.setString(6, user.getLimitId());
            pstmt.setString(7, user.getUserState());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 根据ID删除用户
    public boolean deleteUserById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM users WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 修改用户信息
    public boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE users SET user_name=?,user_sex=?,user_pwd=?,user_tel=?,user_class=?,limit_id=?,user_state=? WHERE user_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getUserSex());
            pstmt.setString(3, user.getUserPwd());
            pstmt.setString(4, user.getUserTel());
            pstmt.setString(5, user.getUserClass());
            pstmt.setString(6, user.getLimitId());
            pstmt.setString(7, user.getUserState());
            pstmt.setInt(8, user.getUserId());
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt);
        }
        return row > 0;
    }

    // 根据ID查询用户
    public User getUserById(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserSex(rs.getString("user_sex"));
                user.setUserPwd(rs.getString("user_pwd"));
                user.setUserTel(rs.getString("user_tel"));
                user.setUserClass(rs.getString("user_class"));
                user.setUserTime(rs.getTimestamp("user_time"));
                user.setLimitId(rs.getString("limit_id"));
                user.setUserState(rs.getString("user_state"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return user;
    }

    // 查询所有用户
    public List<User> getAllUsers() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserSex(rs.getString("user_sex"));
                user.setUserPwd(rs.getString("user_pwd"));
                user.setUserTel(rs.getString("user_tel"));
                user.setUserClass(rs.getString("user_class"));
                user.setUserTime(rs.getTimestamp("user_time"));
                user.setLimitId(rs.getString("limit_id"));
                user.setUserState(rs.getString("user_state"));
                list.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}