package com.library.view;

import com.library.entity.Borrow;
import com.library.service.BorrowService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReturnInternalFrame extends JInternalFrame {
    private final BorrowService borrowService = new BorrowService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField userIdText;
    private int selectedBorrowId;
    private String selectedBookId;

    public ReturnInternalFrame() {
        setTitle("还书登记");
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setLocation(120, 60);
        setLayout(new BorderLayout());

        // 顶部查询栏
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("用户编号："));
        userIdText = new JTextField(10);
        JButton searchBtn = new JButton("查询未归还");
        topPanel.add(userIdText);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // 中间表格
        String[] columns = {"借阅编号", "图书ISBN", "借阅时间", "借阅数量"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 底部还书按钮
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton returnBtn = new JButton("确认还书");
        bottomPanel.add(returnBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // 表格选中事件
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedBorrowId = Integer.parseInt(table.getValueAt(row, 0).toString());
                selectedBookId = table.getValueAt(row, 1).toString();
            }
        });

        // 查询按钮
        searchBtn.addActionListener(e -> {
            String userIdStr = userIdText.getText().trim();
            if (userIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入用户编号！");
                return;
            }
            try {
                int userId = Integer.parseInt(userIdStr);
                loadUnreturnedData(userId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "用户编号必须是数字！");
            }
        });

        // 还书按钮
        returnBtn.addActionListener(e -> {
            if (selectedBorrowId == 0 || selectedBookId == null) {
                JOptionPane.showMessageDialog(this, "请先选中要归还的借阅记录！");
                return;
            }
            boolean result = borrowService.returnBook(selectedBorrowId, selectedBookId);
            if (result) {
                JOptionPane.showMessageDialog(this, "还书成功！");
                // 刷新列表
                int userId = Integer.parseInt(userIdText.getText().trim());
                loadUnreturnedData(userId);
                selectedBorrowId = 0;
                selectedBookId = null;
            } else {
                JOptionPane.showMessageDialog(this, "还书失败！");
            }
        });
    }

    // 加载用户未归还的记录
    private void loadUnreturnedData(int userId) {
        tableModel.setRowCount(0);
        List<Borrow> list = borrowService.getUnreturnedByUserId(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Borrow borrow : list) {
            Object[] row = {
                    borrow.getBorrowId(),
                    borrow.getBookId(),
                    sdf.format(borrow.getBorrowTime()),
                    borrow.getBorrowQty()
            };
            tableModel.addRow(row);
        }
    }
}