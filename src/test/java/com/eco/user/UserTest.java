package com.eco.user;

import com.eco.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * description: UserTest <br>
 * date: 2019/10/27 14:38 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserTest {

    @Autowired
    private IUserService iUserService;

    @Test
    public void CheckUsername(){
        String username = "lice";
        boolean b = iUserService.checkUsername(username);
        System.out.println(b);
    }
}
