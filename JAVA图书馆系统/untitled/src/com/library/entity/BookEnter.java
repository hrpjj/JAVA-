package com.library.entity;

import java.util.Date;

public class BookEnter {
    private Integer enterId;
    private String bookId;
    private Integer enterQty;
    private Date enterTime;
    private Integer operatorId;

    public BookEnter() {
    }

    public Integer getEnterId() {
        return enterId;
    }

    public void setEnterId(Integer enterId) {
        this.enterId = enterId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getEnterQty() {
        return enterQty;
    }

    public void setEnterQty(Integer enterQty) {
        this.enterQty = enterQty;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}