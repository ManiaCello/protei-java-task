package com.maniacello.basics.spring.accountrs.persistence.dao.redis;

import com.maniacello.basics.spring.accountrs.persistence.model.AccountStatus;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author maniacello
 */
public interface AccountStatusRepository extends CrudRepository<AccountStatus, Long> {
}
