package com.zdl.spring.bus.demo.jpa.target;

import com.zdl.spring.bus.demo.jpa.User;
import com.zdl.spring.bus.demo.jpa.source.UserDao;
import com.zdl.spring.bus.endpoint.BaseBusEndpoint;
import com.zdl.spring.bus.endpoint.BusEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by ZDLegend on 2019/4/11 11:55
 */
@BusEndpoint("jpa-user")
public class JpaUserEndpoint implements BaseBusEndpoint<User> {

    private ConcurrentMap<String, User> userMap = new ConcurrentHashMap<>();

    @Autowired
    private UserDao userDao;

    @Override
    public void init() {
        userMap = userDao.findAll().stream().collect(Collectors.toConcurrentMap(User::getId, user -> user));
    }

    @Override
    public void insert(List<User> list) {
        list.forEach(user -> userMap.put(user.getId(), user));
    }

    @Override
    public void delete(List<User> list) {
        list.forEach(user -> userMap.remove(user.getId()));
    }

    Map<String, User> getUserMap() {
        return userMap;
    }
}
