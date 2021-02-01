package com.stevenwan.svlas.controller;


import com.stevenwan.svlas.dto.user.UserDTO;
import com.stevenwan.svlas.entity.UserEntity;
import com.stevenwan.svlas.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author steven.wan
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/addUser")
    @ResponseBody
    public Boolean addUser(@RequestBody UserDTO userDTO) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(userDTO, entity);
        return userService.save(entity);
    }
}

