package com.zdl.spring.bus.demo.jpa.source;

import com.zdl.spring.bus.demo.jpa.User;
import com.zdl.spring.bus.kafka.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by ZDLegend on 2019/4/11 13:37
 */
@Component
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    public List<User> insert(List<User> users) {
        List<User> list = userDao.saveAll(users);
        Sender.insertPublish("jpa-user", null, list);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        User user = userDao.findById(id).orElse(null);
        if (user != null) {
            userDao.deleteById(id);
            Sender.deletePublish("jpa-user", null, Collections.singletonList(user));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<User> modify(List<User> users) {
        List<User> list = userDao.saveAll(users);
        Sender.modifyPublish("jpa-user", null, list);
        return list;
    }
}
