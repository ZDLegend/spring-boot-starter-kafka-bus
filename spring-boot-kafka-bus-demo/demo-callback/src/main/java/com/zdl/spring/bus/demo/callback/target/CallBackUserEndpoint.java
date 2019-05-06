package com.zdl.spring.bus.demo.callback.target;

import com.zdl.spring.bus.demo.callback.User;
import com.zdl.spring.bus.endpoint.BaseBusEndpoint;
import com.zdl.spring.bus.endpoint.BusEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZDLegend on 2019/4/11 11:44
 */
@BusEndpoint(value = "callback-user", callback = true)
public class CallBackUserEndpoint implements BaseBusEndpoint<User> {

    private static final Logger log = LoggerFactory.getLogger(CallBackUserEndpoint.class);

    private static Map<String, User> userMap = new ConcurrentHashMap<>();

    private static Map<String, String> callbackMap = new ConcurrentHashMap<>();

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

    @Override
    public void callBack(String id, String source, Throwable throwable) {
        if (callbackMap.containsKey(id)) {
            try {
                log.info("Resource id:{}, source:{} success.", id, source);
            } finally {
                callbackMap.remove(id);
            }
        }
    }

    public static void register(String id, String s) {
        callbackMap.put(id, s);
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
