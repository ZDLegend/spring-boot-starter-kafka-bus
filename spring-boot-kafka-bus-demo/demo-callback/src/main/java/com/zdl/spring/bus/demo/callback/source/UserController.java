package com.zdl.spring.bus.demo.callback.source;

import com.zdl.spring.bus.demo.callback.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ZDLegend on 2019/4/11 13:44
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public List<User> insert(@RequestBody List<User> users) {
        return userService.insert(users);
    }
}
