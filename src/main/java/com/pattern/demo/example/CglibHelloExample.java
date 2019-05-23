package com.pattern.demo.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc 委托类
 */
public class CglibHelloExample {

    public CglibHelloExample() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibHelloExample.class);

    public String sayHello(String name){
        LOGGER.debug("目标对象的方法执行了");
        return name + " sayHello";
    }

    public String sayByeBye(String name){
        LOGGER.debug("目标对象的方法执行了");
        return name + " sayByeBye";
    }

}
