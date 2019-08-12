package com.maniacello.basics.spring.accountrs.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import org.springframework.data.redis.core.RedisHash;

/**
 *
 * @author maniacello
 */
@RedisHash("AccountStatus")
public class AccountStatus implements Serializable {

    public enum State {
        ONLINE, OFFLINE, AWAY
    }

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private State currentStatus;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private State previousStatus;

    @JsonIgnore
    private Instant timestamp;

    public AccountStatus() {
    }

    public AccountStatus(Long id, State currentStatus, Instant timestamp) {
        this.id = id;
        this.currentStatus = currentStatus;
        this.timestamp = timestamp;
    }

    public AccountStatus(Long id, State currentStatus, State previousStatus, Instant timestamp) {
        this.id = id;
        this.currentStatus = currentStatus;
        this.previousStatus = previousStatus;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public State getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(State currentStatus) {
        this.currentStatus = currentStatus;
    }

    public State getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(State previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public String toString() {
        return "com.maniacello.basics.spring.accountrs.db.model.AccountStatus[ id=" + id + " ]";
    }

}
