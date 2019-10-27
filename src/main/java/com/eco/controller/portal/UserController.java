package com.eco.controller.portal;

import com.eco.common.Const;
import com.eco.common.Res;
import com.eco.common.TokenCache;
import com.eco.pojo.User;
import com.eco.service.IUserService;
import com.eco.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * description: UserController 前台用户controller<br>
 * date: 2019/10/26 23:14 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
@RestController
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private IUserService iUserService;


    @PostMapping("hello.do")
    public String hello() {
        return "hello";
    }

    /**
     * 用户登录(login in)
     *
     * @param username 用户名
     * @param password 密码
     * @param session  登录session（过期时间）
     * @return
     */
    @PostMapping("login.do")
    @ResponseBody
    public Res<User> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        //先判断是否存在该用户，true-存在，false-不存在
        boolean existUsername = iUserService.checkUsername(username);
        if (!existUsername)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名不存在，请先注册");
        User loginUser = iUserService.login(username, password);
        if (loginUser != null) {
            session.setAttribute(Const.CURRENT_USER, loginUser);
            return Res.success(HttpStatus.OK.value(), "登录成功");
        }

        return Res.error(HttpStatus.BAD_REQUEST.value(), "登录失败，用户名或密码错误");
    }

    /**
     * 退出登录
     *
     * @param session 当前用户的session
     * @return
     */
    @PostMapping("logout.do")
    public Res<String> logout(HttpSession session) {
        if (session.getAttribute(Const.CURRENT_USER) != null) {
            session.removeAttribute(Const.CURRENT_USER);
            return Res.error(HttpStatus.OK.value(), "登出成功");
        }
        return Res.error(HttpStatus.BAD_REQUEST.value(), "网络异常");
    }


    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("register.do")
    public Res<String> register(User user) {
        Res<String> res = iUserService.register(user);
        return res;
    }

    /**
     * 注册检验
     *
     * @param criteria 查询条件
     * @param type     检验类型
     * @return
     */
    @PostMapping("check_valid.do")
    public Res<String> checkValid(String criteria, String type) {
        return iUserService.checkValid(criteria, type);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @param session 当前session
     * @return
     */
    @PostMapping("get_user_info.do")
    public Res<User> getUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser != null)
            return Res.success(HttpStatus.OK.value(), currentUser);
        return Res.error(HttpStatus.BAD_REQUEST.value(), "用户未登录，无法获取当前用户信息");
    }

    /**
     * 忘记密码，登录不上，点击找回密码
     * 获取该用户的找回密码问题
     *
     * @param username
     * @return
     */
    @PostMapping("forget_pwd_get_question.do")
    public Res<String> forgetPwdGetQuestion(String username) {
        String question = iUserService.forgetPwdGetQuestion(username);
        if ("".equals(question) || question == null)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名不存在");
        return Res.success(HttpStatus.OK.value(), "成功", question);
    }

    /**
     * 检验忘记密码的问题，成功之后，会返回一个token，用于密码重置的使用，
     * 如果token过期或错误，都重置不了密码
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @PostMapping("forget_check_answer.do")
    public Res<String> forgetCheckAnswer(String username, String question, String answer) {
        boolean exist = iUserService.checkUsername(username);
        if (!exist)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名不存在");
        boolean checkAnswer = iUserService.forgetCheckAnswer(username, question, answer);
        if (!checkAnswer)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "问题和答案不匹配");

        String forgetToken = UUID.randomUUID().toString();
        //设置有效时间
        TokenCache.put(TokenCache.TOKEN_PREFIX + username, forgetToken);
        return Res.success(HttpStatus.OK.value(), "匹配成功", forgetToken);
    }


    /**
     * 重置密码（需要token）
     *
     * @param forgetToken
     * @return
     */
    @PostMapping("reset_pwd_for_forget.do")
    public Res<String> resetPwdForForget(String username, String password, String forgetToken) {
        boolean exist = iUserService.checkUsername(username);
        if (!exist)
            return Res.error(HttpStatus.BAD_REQUEST.value(), "用户名不存在");

        if (StringUtils.isBlank(forgetToken))
            return Res.error(HttpStatus.BAD_REQUEST.value(), "参数错误，token需要被传递");

        String token = TokenCache.get(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token))
            return Res.error(HttpStatus.BAD_REQUEST.value(), "token无效或过期");
        if (StringUtils.equals(token, forgetToken)) {
            //MD5加密新密码
            String MD5Password = MD5Util.MD5EncodeUtf8(password);
            boolean success = iUserService.resetPwdForForget(username, MD5Password);
            if (!success)
                return Res.error(HttpStatus.BAD_REQUEST.value(), "密码修改失败");
        }

        return Res.success(HttpStatus.OK.value(), "密码修改成功");
    }

    /**
     * 登录状态下修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    public Res<String> resetPwd(String oldPassword, String newPassword, HttpSession session) {
        return null;
    }


}
