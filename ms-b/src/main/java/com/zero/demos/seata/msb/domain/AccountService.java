package com.zero.demos.seata.msb.domain;

import com.zero.demos.seata.msb.acl.repository.AccountRepository;
import com.zero.demos.seata.msb.acl.repository.entity.AccountEntity;
import com.zero.demos.seata.msb.ohs.pl.AccountChangeBalanceRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemRequest;
import com.zero.demos.seata.msb.ohs.pl.AccountItemResponse;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository accountRepository;

    public AccountItemResponse getById(Long id) {
        AccountEntity entity = accountRepository.selectById(id);
        if(entity == null) {
            return null;
        }
        AccountItemResponse res = AccountItemResponse.from(entity);
        return res;
    }

    public String addAccount(AccountItemRequest request) {
        Account account =  request.to();
        if(account == null) {
            return "invalid request";
        }
        try {
            Long id = accountRepository.insert(account);
            return "added user id is: " + id;
        } catch (Exception e) {
            logger.error("insert account error", e);
            return "add account failed, may userId exists";
        }
    }

    /**
     * 返回的数值为变更后的余额，如果值为负数，则表示余额变更失败，一般是余额不足（这里明确为余额不足）
     * @param request
     * @return
     */
    public int changeBalance(AccountChangeBalanceRequest request) {
        logger.info("service: account deduction get global transaction id: {}", RootContext.getXID());
///        if(request.getAmount() < 0) {
//            // 扣除余额操作，需要先校验余额
//            AccountEntity account = accountRepository.selectById(request.getId());
//            if(account.getBalance() < Math.abs(request.getAmount())) {
//                logger.warn("account balance not enough, request: {}", request);
//                return -1;
//            }
//        }
        int res = accountRepository.updateBalance(request.getId(), request.getAmount());
        if(res > 0) {
            AccountEntity account = accountRepository.selectById(request.getId());
            if(account != null) {
                return account.getBalance();
            }
        }
        return -1;
    }
}
