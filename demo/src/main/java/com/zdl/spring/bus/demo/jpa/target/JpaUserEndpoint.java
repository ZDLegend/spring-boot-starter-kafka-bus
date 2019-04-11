package com.zdl.spring.bus.demo.jpa.target;

import com.zdl.spring.bus.demo.User;
import com.zdl.spring.bus.demo.jpa.source.UserDao;
import com.zdl.spring.bus.endpoint.BaseBusEndpoint;
import com.zdl.spring.bus.endpoint.BusEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZDLegend on 2019/4/11 11:55
 */
@BusEndpoint("jpa-user")
public class JpaUserEndpoint implements BaseBusEndpoint<User> {

    private Map<String, User> userMap = new ConcurrentHashMap<>();

    @Autowired
    private UserDao userDao;

    @Override
    public void init() {
        userDao.findAll().forEach(user -> userMap.put(user.getId(), user));
    }

    @Override
    public void insert(List<User> list) {
        list.forEach(user -> userMap.put(user.getId(), user));
    }

    @Override
    public void delete(List<User> list) {
        list.forEach(user -> userMap.remove(user.getId()));
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
