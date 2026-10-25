package com.library.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Compensate {
    private Integer cptId;
    private Integer userId;
    private String bookId;
    private String cptCause;
    private BigDecimal cptResult;
    private Date cptTime;
    private Integer operatorId;

    public Compensate() {
    }

    public Integer getCptId() {
        return cptId;
    }

    public void setCptId(Integer cptId) {
        this.cptId = cptId;
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

    public String getCptCause() {
        return cptCause;
    }

    public void setCptCause(String cptCause) {
        this.cptCause = cptCause;
    }

    public BigDecimal getCptResult() {
        return cptResult;
    }

    public void setCptResult(BigDecimal cptResult) {
        this.cptResult = cptResult;
    }

    public Date getCptTime() {
        return cptTime;
    }

    public void setCptTime(Date cptTime) {
        this.cptTime = cptTime;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
}