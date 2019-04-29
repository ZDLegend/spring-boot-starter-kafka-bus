package com.zdl.spring.bus.demo.simple;

import com.zdl.spring.bus.demo.User;
import com.zdl.spring.bus.endpoint.BaseBusEndpoint;
import com.zdl.spring.bus.endpoint.BusEndpoint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZDLegend on 2019/4/11 11:44
 */
@BusEndpoint("user")
public class SimpleUserEndpoint implements BaseBusEndpoint<User> {

    private Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public void init() {
        //do nothing
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
