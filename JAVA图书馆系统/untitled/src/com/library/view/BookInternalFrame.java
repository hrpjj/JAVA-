package com.library.view;

import com.library.entity.Book;
import com.library.service.BookService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookInternalFrame extends JInternalFrame {
    private final BookService bookService = new BookService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField bookIdText, bookNameText, authorText, societyText, priceText, qtyText, sortText;

    public BookInternalFrame() {
        setTitle("图书信息管理");
        setSize(800, 520);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setLocation(20, 20);

        // ========== 顶部工具栏 ==========
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("刷新列表");
        topPanel.add(refreshBtn);
        add(topPanel, BorderLayout.NORTH);

        // ========== 中间表格区 ==========
        String[] columnNames = {"ISBN编号", "书名", "作者", "出版社", "出版日期", "价格", "分类", "总库存", "现存量"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 禁止直接编辑表格
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ========== 底部表单区 ==========
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(2, 7, 6, 6));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ISBN："));
        bookIdText = new JTextField();
        formPanel.add(bookIdText);
        formPanel.add(new JLabel("书名："));
        bookNameText = new JTextField();
        formPanel.add(bookNameText);
        formPanel.add(new JLabel("作者："));
        authorText = new JTextField();
        formPanel.add(authorText);
        formPanel.add(new JLabel("出版社："));
        societyText = new JTextField();
        formPanel.add(societyText);
        formPanel.add(new JLabel("价格："));
        priceText = new JTextField();
        formPanel.add(priceText);
        formPanel.add(new JLabel("分类："));
        sortText = new JTextField();
        formPanel.add(sortText);
        formPanel.add(new JLabel("库存数量："));
        qtyText = new JTextField();
        formPanel.add(qtyText);

        // 按钮区
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("新增图书");
        JButton updateBtn = new JButton("修改图书");
        JButton deleteBtn = new JButton("删除图书");
        JButton clearBtn = new JButton("清空表单");
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // ========== 事件绑定 ==========
        // 表格选中行填充表单
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                bookIdText.setText(table.getValueAt(row, 0).toString());
                bookNameText.setText(table.getValueAt(row, 1).toString());
                authorText.setText(table.getValueAt(row, 2).toString());
                societyText.setText(table.getValueAt(row, 3).toString());
                priceText.setText(table.getValueAt(row, 5).toString());
                sortText.setText(table.getValueAt(row, 6).toString());
                qtyText.setText(table.getValueAt(row, 7).toString());
            }
        });

        // 新增按钮
        addBtn.addActionListener(e -> {
            try {
                Book book = new Book();
                book.setBookId(bookIdText.getText().trim());
                book.setBookName(bookNameText.getText().trim());
                book.setBookAuthor(authorText.getText().trim());
                book.setBookSociety(societyText.getText().trim());
                book.setBookPrice(new BigDecimal(priceText.getText().trim()));
                book.setBookSort(sortText.getText().trim());
                int qty = Integer.parseInt(qtyText.getText().trim());
                book.setBookQty(qty);
                book.setBookQtyx(qty);
                book.setBookTime(new Date());

                if (bookService.addBook(book)) {
                    JOptionPane.showMessageDialog(this, "新增成功！");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "新增失败，ISBN可能已重复！");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误，请检查数字和价格！");
            }
        });

        // 修改按钮
        updateBtn.addActionListener(e -> {
            if (table.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "请先选中要修改的图书！");
                return;
            }
            try {
                Book book = new Book();
                book.setBookId(bookIdText.getText().trim());
                book.setBookName(bookNameText.getText().trim());
                book.setBookAuthor(authorText.getText().trim());
                book.setBookSociety(societyText.getText().trim());
                book.setBookPrice(new BigDecimal(priceText.getText().trim()));
                book.setBookSort(sortText.getText().trim());
                int qty = Integer.parseInt(qtyText.getText().trim());
                book.setBookQty(qty);
                book.setBookQtyx(qty);
                book.setBookTime(new Date());

                if (bookService.updateBook(book)) {
                    JOptionPane.showMessageDialog(this, "修改成功！");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(this, "修改失败！");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "输入格式错误！");
            }
        });

        // 删除按钮
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "请先选中要删除的图书！");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "确认删除该图书？", "删除确认", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String bookId = table.getValueAt(row, 0).toString();
                if (bookService.deleteBook(bookId)) {
                    JOptionPane.showMessageDialog(this, "删除成功！");
                    loadTableData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "删除失败！");
                }
            }
        });

        // 清空、刷新按钮
        clearBtn.addActionListener(e -> clearForm());
        refreshBtn.addActionListener(e -> loadTableData());

        // 初始化加载数据
        loadTableData();
    }

    // 加载表格数据
    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Book> bookList = bookService.getAllBooks();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Book book : bookList) {
            Object[] row = {
                    book.getBookId(),
                    book.getBookName(),
                    book.getBookAuthor(),
                    book.getBookSociety(),
                    sdf.format(book.getBookTime()),
                    book.getBookPrice(),
                    book.getBookSort(),
                    book.getBookQty(),
                    book.getBookQtyx()
            };
            tableModel.addRow(row);
        }
    }

    // 清空表单
    private void clearForm() {
        bookIdText.setText("");
        bookNameText.setText("");
        authorText.setText("");
        societyText.setText("");
        priceText.setText("");
        sortText.setText("");
        qtyText.setText("");
        table.clearSelection();
    }
}