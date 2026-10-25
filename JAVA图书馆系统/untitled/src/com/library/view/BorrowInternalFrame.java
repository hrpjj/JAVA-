package com.library.view;

import com.library.service.BorrowService;
import javax.swing.*;
import java.awt.*;

public class BorrowInternalFrame extends JInternalFrame {
    private final BorrowService borrowService = new BorrowService();
    private JTextField userIdText, bookIdText;

    public BorrowInternalFrame() {
        setTitle("借书登记");
        setSize(400, 250);
        setClosable(true);
        setIconifiable(true);
        setLocation(100, 80);
        setLayout(null);

        // 用户编号
        JLabel userIdLabel = new JLabel("用户编号：");
        userIdLabel.setBounds(60, 40, 80, 25);
        add(userIdLabel);
        userIdText = new JTextField();
        userIdText.setBounds(140, 40, 160, 25);
        add(userIdText);

        // 图书ISBN
        JLabel bookIdLabel = new JLabel("图书ISBN：");
        bookIdLabel.setBounds(60, 90, 80, 25);
        add(bookIdLabel);
        bookIdText = new JTextField();
        bookIdText.setBounds(140, 90, 160, 25);
        add(bookIdText);

        // 借书按钮
        JButton borrowBtn = new JButton("确认借书");
        borrowBtn.setBounds(130, 150, 100, 30);
        add(borrowBtn);

        // 借书事件
        borrowBtn.addActionListener(e -> {
            String userIdStr = userIdText.getText().trim();
            String bookId = bookIdText.getText().trim();

            if (userIdStr.isEmpty() || bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户编号和ISBN不能为空！");
                return;
            }

            try {
                int userId = Integer.parseInt(userIdStr);
                boolean result = borrowService.borrowBook(userId, bookId);
                if (result) {
                    JOptionPane.showMessageDialog(this, "借书成功！");
                    userIdText.setText("");
                    bookIdText.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "借书失败！可能是库存不足或已达借阅上限");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "用户编号必须是数字！");
            }
        });
    }
}