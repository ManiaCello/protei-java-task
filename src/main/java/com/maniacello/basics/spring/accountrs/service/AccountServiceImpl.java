package com.maniacello.basics.spring.accountrs.service;

import com.maniacello.basics.spring.accountrs.persistence.dao.jpa.AccountRepository;
import com.maniacello.basics.spring.accountrs.persistence.dao.redis.AccountStatusRepository;
import com.maniacello.basics.spring.accountrs.persistence.model.Account;
import com.maniacello.basics.spring.accountrs.persistence.model.AccountStatus;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author maniacello
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<AccountStatus> getStatus(Long id) {
        // look for status in redis
        Optional<AccountStatus> accountStatusOpt = accountStatusRepository.findById(id);
        if (accountStatusOpt.isPresent()) {
            return accountStatusOpt;
        }

        // if there is no status, create 'Offline' and save
        Optional<Account> accountOpt = findById(id);
        if (accountOpt.isPresent()) {
            return Optional.of(accountStatusRepository.save(new AccountStatus(id, AccountStatus.State.OFFLINE, Instant.now())));
        }

        // return null if there is no account with specified id
        return Optional.ofNullable(null);
    }

    @Override
    public AccountStatus setStatus(AccountStatus status, AccountStatus.State newStatusState) {
        // update status
        status.setPreviousStatus(status.getCurrentStatus());
        status.setCurrentStatus(newStatusState);
        status.setTimestamp(Instant.now());
        return accountStatusRepository.save(status);
    }
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
}
