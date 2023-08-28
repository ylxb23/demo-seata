package com.zero.demos.seata.msb.ohs.pl;

import com.zero.demos.seata.msb.acl.repository.entity.AccountEntity;
import com.zero.demos.seata.msb.utils.DateUtil;

import java.util.Date;

/**
 * @author ylxb2
 */
public class AccountItemResponse {
    private Long id;

    private String userId;

    private Integer balance;

    /**
     * format: yyyy-MM-dd HH:mm:ss
     */
    private String createTime;

    /**
     * format: yyyy-MM-dd HH:mm:ss
     */
    private String updateTime;

    public static AccountItemResponse from(AccountEntity entity) {
        if(entity == null) {
            return null;
        }
        AccountItemResponse res = new AccountItemResponse();
        res.setId(entity.getId());
        res.setUserId(entity.getUserId());
        res.setBalance(entity.getBalance());
        if(entity.getCreateTime() != null) {
            res.setCreateTime(DateUtil.format(new Date(entity.getCreateTime() * 1000L), DateUtil.DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss));
        }
        if(entity.getUpdateTime() != null) {
            res.setUpdateTime(DateUtil.format(new Date(entity.getUpdateTime() * 1000L), DateUtil.DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss));
        }
        return res;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
