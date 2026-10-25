package com.library.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String bookSociety;
    private Date bookTime;
    private BigDecimal bookPrice;
    private String bookSort;
    private Integer bookQty;
    private Integer bookQtyx;

    public Book() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookSociety() {
        return bookSociety;
    }

    public void setBookSociety(String bookSociety) {
        this.bookSociety = bookSociety;
    }

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookSort() {
        return bookSort;
    }

    public void setBookSort(String bookSort) {
        this.bookSort = bookSort;
    }

    public Integer getBookQty() {
        return bookQty;
    }

    public void setBookQty(Integer bookQty) {
        this.bookQty = bookQty;
    }

    public Integer getBookQtyx() {
        return bookQtyx;
    }

    public void setBookQtyx(Integer bookQtyx) {
        this.bookQtyx = bookQtyx;
    }
}