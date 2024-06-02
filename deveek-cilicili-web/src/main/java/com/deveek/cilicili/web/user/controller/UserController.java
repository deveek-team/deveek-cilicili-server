package com.deveek.cilicili.web.user.controller;

import com.deveek.cilicili.web.user.entity.vo.UserVo;
import com.deveek.cilicili.web.user.service.UserService;
import com.deveek.common.result.Result;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-05-22
 */
@RestController
@PreAuthorize("hasAuthority('USER_R')")
@EnableMethodSecurity
public class UserController {
    @Resource
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('USER_R', 'USER_W')")
    @GetMapping("/api/user/v1")
    public Result<UserVo> getUser(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        return Result.success(userVo);
    }
    
    @GetMapping("/api/user/v1/mask")
    public Result<UserVo> getUserMask(@RequestParam String username) {
        UserVo userVo = userService.getUserVo(username);
        
        userVo.mask();
        
        return Result.success(userVo);
    }
}
