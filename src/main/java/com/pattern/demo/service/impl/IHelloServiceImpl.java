package com.pattern.demo.service.impl;

import com.pattern.demo.service.IHelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc 委托接口实现类
 */
@Service
public class IHelloServiceImpl implements IHelloService {


    private static final Logger LOGGER = LoggerFactory.getLogger(IHelloServiceImpl.class);

    @Override
    public String sayHello(String name) {
        LOGGER.debug("hello service, {}", name);
        return "HelloService: " + name;
    }

    @Override
    public String sayByeBye(String name) {
        LOGGER.debug("byebye service, {}", name);
        return "ByeByeService: " + name;
    }
}
