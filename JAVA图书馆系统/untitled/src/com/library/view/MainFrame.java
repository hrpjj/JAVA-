package com.library.view;

import com.library.entity.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final User currentUser;
    private final JDesktopPane desktopPane;

    public MainFrame(User user) {
        this.currentUser = user;

        // 1. 窗口基础配置
        setTitle("图书管理系统 - 主界面");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2. 桌面面板
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        // 3. 底部状态栏
        JLabel statusBar = new JLabel(" 当前登录用户：" + user.getUserName() + " | 权限ID：" + user.getLimitId());
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        add(statusBar, BorderLayout.SOUTH);

        // 4. 菜单栏
        setJMenuBar(createMenuBar());

        // 最后再显示窗口
        setVisible(true);
    }

    // 单独封装菜单栏，代码更清晰
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // 系统菜单
        JMenu sysMenu = new JMenu("系统");
        JMenuItem exitItem = new JMenuItem("退出登录");
        exitItem.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
        sysMenu.add(exitItem);
        menuBar.add(sysMenu);

        // 图书管理
        JMenu bookMenu = new JMenu("图书管理");
        JMenuItem bookManageItem = new JMenuItem("图书信息管理");
        bookManageItem.addActionListener(e -> openFrame(new BookInternalFrame()));
        bookMenu.add(bookManageItem);
        menuBar.add(bookMenu);

        // 用户管理
        JMenu userMenu = new JMenu("用户管理");
        JMenuItem userManageItem = new JMenuItem("用户信息管理");
        userManageItem.addActionListener(e -> openFrame(new UserInternalFrame()));
        userMenu.add(userManageItem);
        menuBar.add(userMenu);

        // 借阅管理
        JMenu borrowMenu = new JMenu("借阅管理");
        JMenuItem borrowItem = new JMenuItem("借书登记");
        borrowItem.addActionListener(e -> openFrame(new BorrowInternalFrame()));
        JMenuItem returnItem = new JMenuItem("还书登记");
        returnItem.addActionListener(e -> openFrame(new ReturnInternalFrame()));
        borrowMenu.add(borrowItem);
        borrowMenu.add(returnItem);
        menuBar.add(borrowMenu);

        // 库存管理
        JMenu stockMenu = new JMenu("库存管理");
        JMenuItem enterItem = new JMenuItem("图书入库");
        enterItem.addActionListener(e -> openFrame(new BookEnterInternalFrame()));
        JMenuItem outItem = new JMenuItem("图书出库");
        outItem.addActionListener(e -> openFrame(new BookOutInternalFrame()));
        stockMenu.add(enterItem);
        stockMenu.add(outItem);
        menuBar.add(stockMenu);

        // 赔偿管理
        JMenu cptMenu = new JMenu("赔偿管理");
        JMenuItem cptItem = new JMenuItem("赔偿登记");
        cptItem.addActionListener(e -> openFrame(new CompensateInternalFrame()));
        cptMenu.add(cptItem);
        menuBar.add(cptMenu);

        return menuBar;
    }

    // 统一打开内部窗口的方法，避免重复打开多个
    private void openFrame(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    // 测试入口
    public static void main(String[] args) {
        User testUser = new User();
        testUser.setUserId(1);
        testUser.setUserName("管理员");
        testUser.setLimitId("S003");
        new MainFrame(testUser);
    }
}