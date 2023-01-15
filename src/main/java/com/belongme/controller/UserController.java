package com.belongme.controller;

import com.belongme.pojo.User;
import com.belongme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @Title: UserController
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2514:32
 */
@Controller
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    UserService userService;

//    @PostMapping("/login")
//    public String login(String password, HttpServletResponse response) {
//        User user = new User(password);
//        String checkResult = userService.loginCheck(user, response);
//        if (checkResult.equals("登录成功")) {
//            return "file";
//        }
//        return "index";
//    }

}
