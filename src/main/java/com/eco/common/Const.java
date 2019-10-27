package com.eco.common;

/**
 * description: Const 通用常量类<br>
 * date: 2019/10/27 0:04 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1; //管理员

    }
}
