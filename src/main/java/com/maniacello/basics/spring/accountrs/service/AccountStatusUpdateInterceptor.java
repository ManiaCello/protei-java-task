package com.maniacello.basics.spring.accountrs.service;

import com.maniacello.basics.spring.accountrs.ApplicationContextProvider;
import com.maniacello.basics.spring.accountrs.persistence.dao.redis.AccountStatusRepository;
import com.maniacello.basics.spring.accountrs.persistence.model.AccountStatus;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maniacello
 */
public class AccountStatusUpdateInterceptor implements MethodInterceptor {

    private final int statusUpdateRate;

    private AccountStatusRepository accountStatusRepository;

    public AccountStatusUpdateInterceptor(int statusUpdateRate) {
        this.statusUpdateRate = statusUpdateRate;
    }

    /**
     * Checks if account "ONLINE" account status is expired and replaces it with
     * "AWAY" in such case.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        if (invocation.getMethod().getName().contains("find")) {
            accountStatusRepository = ApplicationContextProvider.getApplicationContext().getBean(AccountStatusRepository.class);
            if (retVal instanceof Optional) {
                Optional<AccountStatus> status = (Optional<AccountStatus>) retVal;
                if (status.isPresent()) {
                    status = Optional.of(updateStatus(status.get()));
                }
                return status;
            } else if (retVal instanceof List) {
                return ((List) retVal)
                        .stream()
                        .map(s -> updateStatus((AccountStatus) s))
                        .collect(Collectors.toList());
            }
        }
        return retVal;
    }

    private AccountStatus updateStatus(AccountStatus status) {
        if (status.getCurrentStatus() == AccountStatus.State.ONLINE
                && isPointOlderThan(status.getTimestamp(), statusUpdateRate, ChronoUnit.MINUTES)) {
            status.setCurrentStatus(AccountStatus.State.AWAY);
            status.setTimestamp(Instant.now());
            status = accountStatusRepository.save(status);
        }
        return status;
    }

    /**
     * Returns true if given time point is older than 'timeValue' of given time
     * units and false otherwise.
     *
     * @return true if given time point is older than 'timeValue' of given time
     * units and false otherwise
     */
    private boolean isPointOlderThan(Instant timePoint, int timeValue, TemporalUnit timeUnit) {
        return Instant.now().minus(timeValue, timeUnit).compareTo(timePoint) > 0;
    }

    private static final Logger log = LoggerFactory.getLogger(AccountStatusUpdateInterceptor.class);
}
