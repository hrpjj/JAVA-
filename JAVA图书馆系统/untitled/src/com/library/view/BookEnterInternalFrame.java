package com.library.view;

import com.library.entity.BookEnter;
import com.library.service.BookEnterService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookEnterInternalFrame extends JInternalFrame {
    private final BookEnterService enterService = new BookEnterService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField bookIdText, qtyText, operatorText;

    public BookEnterInternalFrame() {
        setTitle("图书入库登记");
        setSize(650, 450);
        setClosable(true);
        setIconifiable(true);
        setLocation(80, 50);
        setLayout(new BorderLayout());

        // 顶部表单
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("图书ISBN："));
        bookIdText = new JTextField(12);
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("入库数量："));
        qtyText = new JTextField(5);
        topPanel.add(qtyText);
        topPanel.add(new JLabel("操作员ID："));
        operatorText = new JTextField(5);
        topPanel.add(operatorText);
        JButton addBtn = new JButton("确认入库");
        JButton refreshBtn = new JButton("刷新记录");
        topPanel.add(addBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // 记录表格
        String[] columns = {"入库编号", "图书ISBN", "入库数量", "入库时间", "操作员ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 按钮事件
        addBtn.addActionListener(e -> {
            try {
                BookEnter enter = new BookEnter();
                enter.setBookId(bookIdText.getText().trim());
                enter.setEnterQty(Integer.parseInt(qtyText.getText().trim()));
                enter.setOperatorId(Integer.parseInt(operatorText.getText().trim()));

                if (enterService.enterBook(enter)) {
                    JOptionPane.showMessageDialog(this, "入库成功！库存已同步增加");
                    loadTableData();
                    bookIdText.setText("");
                    qtyText.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "入库失败，请检查图书ISBN是否正确");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误，数量和ID必须是数字");
            }
        });
        refreshBtn.addActionListener(e -> loadTableData());
        loadTableData();
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<BookEnter> list = enterService.getAllEnters();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (BookEnter enter : list) {
            Object[] row = {enter.getEnterId(), enter.getBookId(), enter.getEnterQty(), sdf.format(enter.getEnterTime()), enter.getOperatorId()};
            tableModel.addRow(row);
        }
    }
}