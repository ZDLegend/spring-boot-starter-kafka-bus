package com.zdl.spring.bus.demo.jpa.target;

import com.zdl.spring.bus.demo.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZDLegend on 2019/4/11 13:44
 */
@RestController
@RequestMapping("api/user")
public class EndpointController {

    @Autowired
    private JpaUserEndpoint endpoint;

    @GetMapping("{id}")
    public User query(@PathVariable String id) {
        return endpoint.getUserMap().get(id);
    }
}
