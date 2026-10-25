package com.library.service;

import com.library.dao.BookDao;
import com.library.entity.Book;
import java.util.List;

public class BookService {
    private final BookDao bookDao = new BookDao();

    // 新增图书
    public boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    // 删除图书
    public boolean deleteBook(String bookId) {
        return bookDao.deleteBookById(bookId);
    }

    // 修改图书
    public boolean updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    // 根据ISBN查询图书
    public Book getBookById(String bookId) {
        return bookDao.getBookById(bookId);
    }

    // 查询所有图书
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}