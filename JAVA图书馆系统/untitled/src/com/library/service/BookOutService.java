package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.BookOutDao;
import com.library.entity.BookOut;
import java.util.List;

public class BookOutService {
    private final BookOutDao outDao = new BookOutDao();
    private final BookDao bookDao = new BookDao();

    // 出库：添加出库记录 + 减少图书库存
    public boolean outBook(BookOut out) {
        boolean addRecord = outDao.addOut(out);
        boolean reduceStock = bookDao.reduceStock(out.getBookId(), out.getOutQty());
        return addRecord && reduceStock;
    }

    // 查询所有出库记录
    public List<BookOut> getAllOuts() {
        return outDao.getAllOuts();
    }
}