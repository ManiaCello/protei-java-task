package com.maniacello.basics.spring.accountrs.api;

import com.fasterxml.jackson.databind.node.TextNode;
import com.maniacello.basics.spring.accountrs.persistence.model.Account;
import com.maniacello.basics.spring.accountrs.persistence.model.AccountStatus;
import com.maniacello.basics.spring.accountrs.service.AccountService;
import com.maniacello.basics.spring.accountrs.util.Responses;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Controller for /accounts endpoint.
 *
 * @author maniacello
 */
@Controller
@RequestMapping(path = "${api.path}" + "/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable("id") Account account, @PathVariable("id") Long id) {
        return account != null
                ? Responses.OK(account)
                : Responses.NOT_FOUND("An account with id '" + id + "' not found.");
    }

    @PostMapping(headers = "Content-Type=application/json")
    public ResponseEntity<Object> addAccount(@RequestBody Account account) {
        if (account != null) {
            try {
                return Responses.CREATED(accountService.addAccount(account));
            } catch (RuntimeException e) {
                log.debug("Saving an Account entity failure: " + e.getMessage());
                if (e instanceof DataAccessException) {
                    return Responses.BAD_REQUEST();
                } else {
                    return Responses.BAD_GATEWAY();
                }
            }
        }
        return Responses.BAD_REQUEST();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Object> getStatus(@PathVariable Long id) {
        Optional<AccountStatus> accountStatusOpt = accountService.getStatus(id);
        return accountStatusOpt.isPresent()
                ? Responses.OK(accountStatusOpt.get())
                : Responses.NOT_FOUND("An account with id '" + id + "' not found.");
    }

    @PutMapping(path = "/{id}/status", headers = "Content-Type=application/json")
    public ResponseEntity<Object> setStatus(@PathVariable Long id, @RequestBody TextNode status) {
        AccountStatus.State statusState;
        // check specified status
        try {
            statusState = AccountStatus.State.valueOf(status.asText());
        } catch (IllegalArgumentException | NullPointerException e) {
            return Responses.BAD_REQUEST("Status '" + status.asText() + "' doesn't exist.");
        }

        // check specified id
        Optional<AccountStatus> accountStatus = accountService.getStatus(id);
        if (!accountStatus.isPresent()) {
            return Responses.NOT_FOUND("An account with id '" + id + "' not found.");
        }

        // set status
        return Responses.OK(accountService.setStatus(accountStatus.get(), statusState));
    }

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
}
