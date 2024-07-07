package com.deveek.cilicili.web.common.bloomfilter;

import com.deveek.cilicili.web.common.user.model.vo.UserVo;
import com.deveek.common.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.deveek.cilicili.web.common.bloomfilter.BloomResultWrapperEnum.USER_NOT_FOUND;

/**
 * @author Shooter
 * 注解使用示例
 */
@RestController
@Slf4j
public class BloomController {

    /**
     * 注解使用示例接口
     */
    @GetMapping("/api/v1/user/{username}/{userAccount}")
    @BloomFilterCheck(
            value = "T(com.deveek.cilicili.web.common.bloomfilter.BloomFilterConstant).BLOOM_FILTER_USERNAME"
                    + "+'-'+"
                    + "#username",
            result = USER_NOT_FOUND
    )
    public Result<UserVo> getUser(@PathVariable String username, @PathVariable String userAccount) {
        // @BloomFilterCheck 注解会对 BLOOM_FILTER_USERNAME 这个过滤器中没有该 username 的进行拦截，并抛出 USER_NOT_FOUND 异常，进来的都是存在过滤器中的
        log.info("hello 啊树哥: {}", username);
        return Result.success();
    }

}
