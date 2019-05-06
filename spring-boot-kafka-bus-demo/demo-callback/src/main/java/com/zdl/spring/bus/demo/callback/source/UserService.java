package com.zdl.spring.bus.demo.callback.source;

import com.zdl.spring.bus.demo.callback.User;
import com.zdl.spring.bus.demo.callback.target.CallBackUserEndpoint;
import com.zdl.spring.bus.message.BusMessage;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zdl.spring.bus.endpoint.EndpointManage.OPERATION_ADD;
import static com.zdl.spring.bus.kafka.Sender.busProperties;
import static com.zdl.spring.bus.kafka.Sender.syncPublish;

/**
 * Created by ZDLegend on 2019/4/11 13:37
 */
@Service
public class UserService {
    public List<User> insert(List<User> users) {
        BusMessage<User> message = BusMessage.instance(users)
                .operation(OPERATION_ADD)
                .endPointId("callback-user");
        CallBackUserEndpoint.register(message.getId(), "");
        syncPublish(busProperties.getTopic(), message);
        return users;
    }
}
