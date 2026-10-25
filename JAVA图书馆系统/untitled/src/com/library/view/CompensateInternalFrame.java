package com.library.view;

import com.library.entity.Compensate;
import com.library.service.CompensateService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class CompensateInternalFrame extends JInternalFrame {
    private final CompensateService cptService = new CompensateService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField userIdText, bookIdText, causeText, amountText, operatorText;

    public CompensateInternalFrame() {
        setTitle("赔偿登记");
        setSize(750, 480);
        setClosable(true);
        setIconifiable(true);
        setLocation(90, 60);
        setLayout(new BorderLayout());

        // 顶部表单
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("用户ID："));
        userIdText = new JTextField();
        formPanel.add(userIdText);
        formPanel.add(new JLabel("图书ISBN："));
        bookIdText = new JTextField();
        formPanel.add(bookIdText);
        formPanel.add(new JLabel("赔偿原因："));
        causeText = new JTextField();
        formPanel.add(causeText);
        formPanel.add(new JLabel("赔偿金额："));
        amountText = new JTextField();
        formPanel.add(amountText);
        formPanel.add(new JLabel("操作员ID："));
        operatorText = new JTextField();
        formPanel.add(operatorText);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("登记赔偿");
        JButton refreshBtn = new JButton("刷新记录");
        btnPanel.add(addBtn);
        btnPanel.add(refreshBtn);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(btnPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        // 记录表格
        String[] columns = {"赔偿编号", "用户ID", "图书ISBN", "赔偿原因", "赔偿金额", "登记时间", "操作员ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 按钮事件
        addBtn.addActionListener(e -> {
            try {
                Compensate cpt = new Compensate();
                cpt.setUserId(Integer.parseInt(userIdText.getText().trim()));
                cpt.setBookId(bookIdText.getText().trim());
                cpt.setCptCause(causeText.getText().trim());
                cpt.setCptResult(new BigDecimal(amountText.getText().trim()));
                cpt.setOperatorId(Integer.parseInt(operatorText.getText().trim()));

                if (cptService.addCompensate(cpt)) {
                    JOptionPane.showMessageDialog(this, "赔偿登记成功！");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "登记失败，请检查用户ID和ISBN");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误，金额请输入数字");
            }
        });
        refreshBtn.addActionListener(e -> loadTableData());
        loadTableData();
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Compensate> list = cptService.getAllCompensates();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Compensate cpt : list) {
            Object[] row = {cpt.getCptId(), cpt.getUserId(), cpt.getBookId(), cpt.getCptCause(), cpt.getCptResult(), sdf.format(cpt.getCptTime()), cpt.getOperatorId()};
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        userIdText.setText("");
        bookIdText.setText("");
        causeText.setText("");
        amountText.setText("");
        operatorText.setText("");
    }
}