package com.zdl.spring.bus.demo.jpa.source;

import com.zdl.spring.bus.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public List<User> modify(List<User> users) {
        return userService.modify(users);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }
}
