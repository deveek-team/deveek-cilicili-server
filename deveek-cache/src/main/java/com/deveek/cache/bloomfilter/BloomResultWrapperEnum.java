package com.deveek.cache.bloomfilter;

import com.deveek.cilicili.web.common.user.constant.UserResult;
import com.deveek.common.constant.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 布隆异常结果枚举包装类
 * @author Shooter
 */
@RequiredArgsConstructor
public enum BloomResultWrapperEnum {

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(UserResult.USER_NOT_FOUND);

    @Getter
    private final Result result;

}
