package com.library.service;

import com.library.dao.CompensateDao;
import com.library.entity.Compensate;
import java.util.List;

public class CompensateService {
    private final CompensateDao compensateDao = new CompensateDao();

    public boolean addCompensate(Compensate compensate) {
        return compensateDao.addCompensate(compensate);
    }

    public List<Compensate> getAllCompensates() {
        return compensateDao.getAllCompensates();
    }
}