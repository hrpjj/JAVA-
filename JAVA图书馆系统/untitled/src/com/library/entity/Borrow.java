package com.library.entity;

import java.util.Date;

public class Borrow {
    private Integer borrowId;
    private Integer userId;
    private String bookId;
    private Date borrowTime;
    private Integer borrowQty;
    private Date returnTime;

    public Borrow() {
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Integer getBorrowQty() {
        return borrowQty;
    }

    public void setBorrowQty(Integer borrowQty) {
        this.borrowQty = borrowQty;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
}