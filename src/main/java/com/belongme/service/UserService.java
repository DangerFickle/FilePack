package com.belongme.service;

import com.belongme.pojo.User;

import javax.servlet.http.HttpServletResponse;

/**
 * @Title: UserService
 * @ProjectName FilePack
 * @Description: TODO
 * @Author DengChao
 * @Date 2022/11/2514:41
 */
public interface UserService {
    User loginCheck(User user, HttpServletResponse response);
}
