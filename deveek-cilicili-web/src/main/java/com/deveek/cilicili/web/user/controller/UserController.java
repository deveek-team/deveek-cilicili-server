package com.deveek.cilicili.web.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.deveek.cilicili.web.common.user.model.dto.UserRegisterDto;
import com.deveek.cilicili.web.common.user.model.dto.UserSendCodeDto;
import com.deveek.cilicili.web.common.user.model.po.UserPo;
import com.deveek.cilicili.web.common.user.model.vo.UserVo;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.common.constant.Result;
import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author harvey 
 */
@RestController
@PreAuthorize("hasAuthority('USER_R')")
@EnableMethodSecurity
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource(name = "userBloomFilter")
    private RBloomFilter userBloomFilter;

    @GetMapping("/api/v1/user/get_user")
    public Result<UserVo> getUser(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        return Result.success(userVo);
    }
    
    @GetMapping("/api/v1/user/get_user_mask")
    public Result<UserVo> getUserMask(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        userVo.mask();
        
        return Result.success(userVo);
    }

    @PostMapping("/api/v1/user/code")
    public Result<Void> sendCode(@RequestBody UserSendCodeDto userSendCodeDto) {
        String username = userSendCodeDto.getUsername();
        String email = userSendCodeDto.getEmail();
        userService.sendCode(username, email);

        return Result.success();
    }

    @Transactional
    @PostMapping("/api/v1/user/register")
    public Result<Void> register(@RequestBody UserRegisterDto userRegisterDto) {
        String email = userRegisterDto.getEmail();
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String verifyCode = userRegisterDto.getVerifyCode();

        userService.register(username,password,email,verifyCode);

        UserPo userPo = BeanUtil.copyProperties(userRegisterDto, UserPo.class);

        password = userPo.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        userPo.setPassword(encodedPassword);

        userService.save(userPo);

        userBloomFilter.add(userPo.getId());

        return Result.success();
    }
}
