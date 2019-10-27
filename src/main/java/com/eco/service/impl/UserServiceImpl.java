package com.eco.service.impl;

import com.eco.common.Const;
import com.eco.common.Res;
import com.eco.mapper.UserMapper;
import com.eco.pojo.User;
import com.eco.service.IUserService;
import com.eco.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * description: UserServiceImpl  <br>
 * date: 2019/10/26 23:24 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检查用户是否存在
     *
     * @param username 用户名
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        int count = userMapper.checkUsername(username);
        if (count == 0)
            return false;
        return true;
    }

    /**
     * 用户登录(MD5)
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User loginUser = userMapper.login(username, md5Password);
        if (loginUser != null)
            return loginUser;
        return null;
    }

    //检查email是否存在
    @Override
    public boolean checkEmail(String email) {
        int checkEmail = userMapper.checkEmail(email);
        if (checkEmail > 0)
            return true;
        return false;
    }

    //用户注册
    @Override
    public Res<String> register(User user) {
        //判断该用户是否存在
        int checkUsername = userMapper.checkUsername(user.getUsername());
        if (checkUsername > 0)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名已存在");
        //判断email是否存在
        int checkEmail = userMapper.checkEmail(user.getEmail());
        if (checkEmail > 0)
            return Res.error(HttpStatus.CONFLICT.value(), "email已存在");
        //设置为普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int row = userMapper.insert(user);
        if (row == 0)
            return Res.success(HttpStatus.OK.value(), "注册失败");

        return Res.error(HttpStatus.BAD_REQUEST.value(), "注册成功");
    }

    public Res<String> checkValid(String criteria, String type) {
        int resultCount;
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.USERNAME.equals(type)) {
                resultCount = userMapper.checkUsername(criteria);
                if (resultCount > 0)
                    return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名已存在");

            }
            if (Const.EMAIL.equals(type)) {
                resultCount = userMapper.checkEmail(criteria);
                if (resultCount > 0)
                    return Res.error(HttpStatus.BAD_REQUEST.value(), "email已存在");
            }
        } else {
            return Res.error(HttpStatus.BAD_REQUEST.value(), "参数错误");
        }
        return Res.success(HttpStatus.OK.value(), "检验成功");
    }

    //获取用户问题
    @Override
    public String forgetPwdGetQuestion(String username) {
        return userMapper.selectUserQuestion(username);
    }

    //检验用户的问题和答案
    @Override
    public boolean forgetCheckAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkQuestionAndAnswer(username, question, answer);
        if (resultCount > 0)
            return true; //检验成功
        return false;//问题和密码对不上
    }


    //找回密码重置密码
    @Override
    public boolean resetPwdForForget(String username, String newPassword) {
        int resultCount = userMapper.updatePwdByUsername(username, newPassword);
        if (resultCount > 0)
            return true;
        return false;
    }


}
