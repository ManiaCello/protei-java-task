package com.maniacello.basics.spring.accountrs.service;

import com.maniacello.basics.spring.accountrs.persistence.model.Account;
import com.maniacello.basics.spring.accountrs.persistence.model.AccountStatus;
import java.util.Optional;

/**
 *
 * @author maniacello
 */
public interface AccountService {

    Account addAccount(Account account);

    Optional<Account> findById(Long id);

    Optional<AccountStatus> getStatus(Long id);

    AccountStatus setStatus(AccountStatus status, AccountStatus.State newStatusState);

}
