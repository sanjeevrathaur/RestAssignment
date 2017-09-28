package com.test.dao;

import com.test.model.Account;
import com.test.utils.BusinessException;

public interface AccountDAO {

    void save(Account account) throws BusinessException;
    void update(Account account) throws BusinessException;
    void getAccounts() throws BusinessException;
    void getAccount(Integer id) throws BusinessException;

}
