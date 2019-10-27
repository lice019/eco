package com.eco.service;

import com.eco.common.Res;
import com.eco.pojo.User;

/**
 * description: IUserService <br>
 * date: 2019/10/26 23:23 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public interface IUserService {

    //检查用户是否存在
    boolean checkUsername(String username);

    //用户登录
    User login(String username, String password);

    //检查email是否存在
    boolean checkEmail(String email);

    //用户注册
    Res<String> register(User user);

    //检验
    Res<String> checkValid(String criteria, String type);

    //获取用户的问题
    String forgetPwdGetQuestion(String username);

    //检验用户的问题和答案
    boolean forgetCheckAnswer(String username, String question, String answer);

    //重置密码
    boolean resetPwdForForget(String username, String password);

}
