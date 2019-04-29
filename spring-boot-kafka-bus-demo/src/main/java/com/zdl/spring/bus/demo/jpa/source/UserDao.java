package com.zdl.spring.bus.demo.jpa.source;

import com.zdl.spring.bus.demo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by ZDLegend on 2019/4/11 11:57
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
}
