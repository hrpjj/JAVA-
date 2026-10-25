package com.library.view;

import com.library.entity.BookOut;
import com.library.service.BookOutService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookOutInternalFrame extends JInternalFrame {
    private final BookOutService outService = new BookOutService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField bookIdText, qtyText, operatorText;

    public BookOutInternalFrame() {
        setTitle("图书出库登记");
        setSize(650, 450);
        setClosable(true);
        setIconifiable(true);
        setLocation(100, 70);
        setLayout(new BorderLayout());

        // 顶部表单
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("图书ISBN："));
        bookIdText = new JTextField(12);
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("出库数量："));
        qtyText = new JTextField(5);
        topPanel.add(qtyText);
        topPanel.add(new JLabel("操作员ID："));
        operatorText = new JTextField(5);
        topPanel.add(operatorText);
        JButton addBtn = new JButton("确认出库");
        JButton refreshBtn = new JButton("刷新记录");
        topPanel.add(addBtn);
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // 记录表格
        String[] columns = {"出库编号", "图书ISBN", "出库数量", "出库时间", "操作员ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 按钮事件
        addBtn.addActionListener(e -> {
            try {
                BookOut out = new BookOut();
                out.setBookId(bookIdText.getText().trim());
                out.setOutQty(Integer.parseInt(qtyText.getText().trim()));
                out.setOperatorId(Integer.parseInt(operatorText.getText().trim()));

                if (outService.outBook(out)) {
                    JOptionPane.showMessageDialog(this, "出库成功！库存已同步减少");
                    loadTableData();
                    bookIdText.setText("");
                    qtyText.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "出库失败，库存不足或ISBN错误");
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
        List<BookOut> list = outService.getAllOuts();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (BookOut out : list) {
            Object[] row = {out.getOutId(), out.getBookId(), out.getOutQty(), sdf.format(out.getOutTime()), out.getOperatorId()};
            tableModel.addRow(row);
        }
    }
}