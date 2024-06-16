package com.deveek.common.constant;

/**
 * @author harvey
 */
public class BaseResult extends Result {
    public static final Result USER_NOT_FOUND = new Result(601, "user not found");
    
    public static final Result PARAM_INVALID = new Result(602, "param is invalid");
    
    public static final Result PAGE_NO_INVALID = new Result(603, "page number is invalid");
    
    public static final Result PAGE_SIZE_INVALID = new Result(604, "page size is invalid");
}
