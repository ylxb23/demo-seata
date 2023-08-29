package com.zero.demos.seata.msa.ohs.exceptions;

import feign.FeignException;

/**
 * @author ylxb2
 */
public class FeignBizException extends FeignException {
    public FeignBizException(int status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
