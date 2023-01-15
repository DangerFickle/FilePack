package com.belongme.service.impl;

import com.belongme.pojo.User;
import com.belongme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: UserServiceImpl
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2514:43
 */
@Service("userService")
@ConfigurationProperties(prefix = "user")
public class UserServiceImpl implements UserService {

    @Value("password")
    String password;

    @Override
    public User loginCheck(User user, HttpServletResponse response) {
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }

    }
}
