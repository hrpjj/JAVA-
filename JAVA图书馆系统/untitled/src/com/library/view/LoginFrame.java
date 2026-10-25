package com.library.view;

import com.library.entity.User;
import com.library.service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private final UserService userService = new UserService();

    // 组件声明
    private JTextField usernameText;
    private JPasswordField passwordText;

    public LoginFrame() {
        // 窗口基础设置
        setTitle("图书管理系统 - 登录");
        setSize(400, 300);
        setLocationRelativeTo(null); // 窗口居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 主面板
        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // 标题
        JLabel titleLabel = new JLabel("图书管理系统");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setBounds(120, 30, 200, 40);
        panel.add(titleLabel);

        // 用户名标签+输入框
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setBounds(70, 90, 60, 25);
        panel.add(userLabel);
        usernameText = new JTextField(20);
        usernameText.setBounds(140, 90, 160, 25);
        panel.add(usernameText);

        // 密码标签+输入框
        JLabel pwdLabel = new JLabel("密  码：");
        pwdLabel.setBounds(70, 130, 60, 25);
        panel.add(pwdLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(140, 130, 160, 25);
        panel.add(passwordText);

        // 登录按钮
        JButton loginBtn = new JButton("登录");
        loginBtn.setBounds(100, 190, 80, 30);
        panel.add(loginBtn);

        // 重置按钮
        JButton resetBtn = new JButton("重置");
        resetBtn.setBounds(210, 190, 80, 30);
        panel.add(resetBtn);

        // 登录按钮点击事件
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText().trim();
                String password = new String(passwordText.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
                    return;
                }

                User user = userService.login(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(null, "登录成功！欢迎 " + user.getUserName());
                    // 登录成功后关闭登录窗，打开主界面（后面我们再写主界面）
                    dispose();
                    new MainFrame(user); // 后面写完主界面再打开这句
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误，或账号已停用！");
                }
            }
        });

        // 重置按钮点击事件
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameText.setText("");
                passwordText.setText("");
            }
        });

        setVisible(true);
    }

    // 程序入口
    public static void main(String[] args) {
        new LoginFrame();
    }
}