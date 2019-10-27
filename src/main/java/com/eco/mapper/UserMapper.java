package com.eco.mapper;

import com.eco.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //检查用户是否存在
    int checkUsername(String username);

    //登录
    User login(@Param("username") String username, @Param("password") String password);

    //检查email是否存在
    int checkEmail(String email);

    //获取用户问题
    String selectUserQuestion(String username);

    //检验用户的问题和答案
    int checkQuestionAndAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    //重置密码
    int updatePwdByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

}