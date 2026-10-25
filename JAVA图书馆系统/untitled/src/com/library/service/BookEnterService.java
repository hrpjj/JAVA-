package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.BookEnterDao;
import com.library.entity.BookEnter;
import java.util.List;

public class BookEnterService {
    private final BookEnterDao enterDao = new BookEnterDao();
    private final BookDao bookDao = new BookDao();

    // 入库：添加入库记录 + 增加图书库存
    public boolean enterBook(BookEnter enter) {
        boolean addRecord = enterDao.addEnter(enter);
        boolean addStock = bookDao.addStock(enter.getBookId(), enter.getEnterQty());
        return addRecord && addStock;
    }

    // 查询所有入库记录
    public List<BookEnter> getAllEnters() {
        return enterDao.getAllEnters();
    }
}