package com.maniacello.basics.spring.accountrs.config;

import com.maniacello.basics.spring.accountrs.persistence.dao.redis.AccountStatusRepository;
import com.maniacello.basics.spring.accountrs.service.AccountStatusUpdateInterceptor;
import java.io.Serializable;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;

/**
 * RedisRepositoryFactoryBean subclass that adds around advice
 * {@link com.maniacello.basics.spring.accountrs.service.AccountStatusUpdateInterceptor}
 * to "find*" methods of AccountStatusRepository.
 *
 * @author maniacello
 */
@PropertySource("classpath:/application.properties")
public class CustomRedisRepositoryFactoryBean<R extends Repository<T, I>, T, I extends Serializable> extends RedisRepositoryFactoryBean<R, T, I> {

    @Value("${account.status.updateRate}")
    private int statusUpdateRate;

    public CustomRedisRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RedisRepositoryFactory createRepositoryFactory(KeyValueOperations operations, Class<? extends AbstractQueryCreator<?, ?>> queryCreator, Class<? extends RepositoryQuery> repositoryQueryType) {
        RedisRepositoryFactory factory = super.createRepositoryFactory(operations, queryCreator, repositoryQueryType);
        factory.addRepositoryProxyPostProcessor(getRepositoryProxyPostProcessor());
        return factory;
    }

    public RepositoryProxyPostProcessor getRepositoryProxyPostProcessor() {
        return (factory, repositoryInformation) -> {
            if (repositoryInformation.getRepositoryInterface().equals(AccountStatusRepository.class)) {
                AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                pointcut.setExpression("execution(public * org.springframework.data.repository.Repository+.find*(..)))");
                Advisor advisor = new DefaultPointcutAdvisor(pointcut, getUpdateInterceptor());
                factory.addAdvisor(advisor);
            }
        };
    }

    public MethodInterceptor getUpdateInterceptor() {
        return new AccountStatusUpdateInterceptor(statusUpdateRate);
    }

}
