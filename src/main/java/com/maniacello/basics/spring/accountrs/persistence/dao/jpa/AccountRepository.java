package com.maniacello.basics.spring.accountrs.persistence.dao.jpa;

import com.maniacello.basics.spring.accountrs.persistence.model.Account;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author maniacello
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

}
