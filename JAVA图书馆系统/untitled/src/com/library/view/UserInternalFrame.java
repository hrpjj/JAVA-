package com.library.view;

import com.library.entity.User;
import com.library.service.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserInternalFrame extends JInternalFrame {
    private final UserService userService = new UserService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField userNameText, sexText, pwdText, telText, classText, limitIdText, stateText;
    private Integer selectedUserId; // 记录选中的用户ID

    public UserInternalFrame() {
        setTitle("用户信息管理");
        setSize(850, 520);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setLocation(40, 20);

        // 顶部工具栏
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("刷新列表");
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // 中间表格
        String[] columns = {"用户ID", "用户名", "性别", "密码", "电话", "班级", "权限ID", "状态", "注册时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 底部表单
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 7, 6, 6));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("用户名："));
        userNameText = new JTextField();
        formPanel.add(userNameText);
        formPanel.add(new JLabel("性别："));
        sexText = new JTextField("男");
        formPanel.add(sexText);
        formPanel.add(new JLabel("密码："));
        pwdText = new JTextField("123456");
        formPanel.add(pwdText);
        formPanel.add(new JLabel("电话："));
        telText = new JTextField();
        formPanel.add(telText);
        formPanel.add(new JLabel("班级："));
        classText = new JTextField();
        formPanel.add(classText);
        formPanel.add(new JLabel("权限ID："));
        limitIdText = new JTextField("S001");
        formPanel.add(limitIdText);
        formPanel.add(new JLabel("账号状态："));
        stateText = new JTextField("使用");
        formPanel.add(stateText);

        // 按钮区
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("新增用户");
        JButton updateBtn = new JButton("修改用户");
        JButton deleteBtn = new JButton("删除用户");
        JButton clearBtn = new JButton("清空表单");
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // ========== 事件绑定 ==========
        // 表格选中填充表单
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedUserId = Integer.parseInt(table.getValueAt(row, 0).toString());
                userNameText.setText(table.getValueAt(row, 1).toString());
                sexText.setText(table.getValueAt(row, 2).toString());
                pwdText.setText(table.getValueAt(row, 3).toString());
                telText.setText(table.getValueAt(row, 4).toString());
                classText.setText(table.getValueAt(row, 5).toString());
                limitIdText.setText(table.getValueAt(row, 6).toString());
                stateText.setText(table.getValueAt(row, 7).toString());
            }
        });

        // 新增用户
        addBtn.addActionListener(e -> {
            try {
                User user = new User();
                user.setUserName(userNameText.getText().trim());
                user.setUserSex(sexText.getText().trim());
                user.setUserPwd(pwdText.getText().trim());
                user.setUserTel(telText.getText().trim());
                user.setUserClass(classText.getText().trim());
                user.setLimitId(limitIdText.getText().trim());
                user.setUserState(stateText.getText().trim());

                if (userService.addUser(user)) {
                    JOptionPane.showMessageDialog(this, "新增成功！默认密码123456");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "新增失败！");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误！");
            }
        });

        // 修改用户
        updateBtn.addActionListener(e -> {
            if (selectedUserId == null) {
                JOptionPane.showMessageDialog(this, "请先选中要修改的用户！");
                return;
            }
            try {
                User user = new User();
                user.setUserId(selectedUserId);
                user.setUserName(userNameText.getText().trim());
                user.setUserSex(sexText.getText().trim());
                user.setUserPwd(pwdText.getText().trim());
                user.setUserTel(telText.getText().trim());
                user.setUserClass(classText.getText().trim());
                user.setLimitId(limitIdText.getText().trim());
                user.setUserState(stateText.getText().trim());

                if (userService.updateUser(user)) {
                    JOptionPane.showMessageDialog(this, "修改成功！");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this, "修改失败！");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误！");
            }
        });

        // 删除用户
        deleteBtn.addActionListener(e -> {
            if (selectedUserId == null) {
                JOptionPane.showMessageDialog(this, "请先选中要删除的用户！");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "确认删除该用户？", "删除确认", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (userService.deleteUser(selectedUserId)) {
                    JOptionPane.showMessageDialog(this, "删除成功！");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败！");
                }
            }
        });

        // 清空、刷新
        clearBtn.addActionListener(e -> clearForm());
        refreshBtn.addActionListener(e -> loadTableData());

        loadTableData();
    }

    // 加载表格数据
    private void loadTableData() {
        tableModel.setRowCount(0);
        List<User> userList = userService.getAllUsers();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (User user : userList) {
            Object[] row = {
                    user.getUserId(),
                    user.getUserName(),
                    user.getUserSex(),
                    user.getUserPwd(),
                    user.getUserTel(),
                    user.getUserClass(),
                    user.getLimitId(),
                    user.getUserState(),
                    sdf.format(user.getUserTime())
            };
            tableModel.addRow(row);
        }
    }

    // 清空表单
    private void clearForm() {
        selectedUserId = null;
        userNameText.setText("");
        sexText.setText("男");
        pwdText.setText("123456");
        telText.setText("");
        classText.setText("");
        limitIdText.setText("S001");
        stateText.setText("使用");
        table.clearSelection();
    }
}