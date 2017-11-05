package com.test.dao;

import com.test.model.Account;
import com.test.utils.BusinessException;

import java.util.List;

/**
  This interface will caontains all methods related to customer account.
*/

public interface AccountDAO {

    void save(Account account) throws BusinessException;
    void update(Account account) throws BusinessException;
    List<Account> getAccounts() throws BusinessException;
    Account getAccount(Integer id) throws BusinessException;

}
