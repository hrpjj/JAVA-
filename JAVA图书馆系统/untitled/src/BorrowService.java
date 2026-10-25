package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.BorrowDao;
import com.library.dao.UserDao;
import com.library.entity.Borrow;
import com.library.entity.User;
import com.library.entity.UserLimit;
import java.util.List;

public class BorrowService {
    private final BorrowDao borrowDao = new BorrowDao();
    private final BookDao bookDao = new BookDao();
    private final UserDao userDao = new UserDao();

    // 借书业务：校验可借数量 + 校验库存，成功则扣库存+生成借阅记录
    public boolean borrowBook(int userId, String bookId) {
        // 1. 查询用户当前未归还的图书数量
        List<Borrow> unreturnedList = borrowDao.getUnreturnedByUserId(userId);
        int borrowedCount = unreturnedList.size();

        // 2. 查询用户权限对应的可借上限（简化版，直接根据权限ID判断）
        User user = userDao.getUserById(userId);
        int maxQty = 3; // 默认学生3本
        if (user != null) {
            switch (user.getLimitId()) {
                case "S002": maxQty = 10; break; // 教师
                case "S003": maxQty = 20; break; // 管理员
                case "S004": maxQty = 30; break; // 领导
            }
        }
        if (borrowedCount >= maxQty) {
            System.out.println("已达最大可借数量，无法借阅");
            return false;
        }

        // 3. 扣减图书库存
        boolean stockResult = bookDao.decreaseStock(bookId);
        if (!stockResult) {
            System.out.println("库存不足，无法借阅");
            return false;
        }

        // 4. 生成借阅记录
        Borrow borrow = new Borrow();
        borrow.setUserId(userId);
        borrow.setBookId(bookId);
        borrow.setBorrowQty(1);
        return borrowDao.addBorrow(borrow);
    }

    // 还书业务：更新归还时间 + 恢复库存
    public boolean returnBook(int borrowId, String bookId) {
        boolean result1 = borrowDao.returnBook(borrowId);
        boolean result2 = bookDao.increaseStock(bookId);
        return result1 && result2;
    }

    // 查询用户未归还记录
    public List<Borrow> getUnreturnedByUserId(int userId) {
        return borrowDao.getUnreturnedByUserId(userId);
    }

    // 查询所有借阅记录
    public List<Borrow> getAllBorrows() {
        return borrowDao.getAllBorrows();
    }
}