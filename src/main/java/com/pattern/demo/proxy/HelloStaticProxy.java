package com.pattern.demo.proxy;

import com.pattern.demo.service.IHelloService;
import com.pattern.demo.service.impl.IHelloServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc 静态代理类
 *
 *
 * 首先, 定义接口和接口的实现类, 然后定义接口的代理对象, 将接口的实例注入到代理对象中,
 * 然后通过代理对象去调用真正的实现类，实现过程非常简单也比较容易理解, 静态代理的代理关系在编译期间就已经确定了的。
 * 它适合于代理类较少且确定的情况。它可实现在怒修改委托类代码的情况下做一些额外的处理，比如包装礼盒，实现客户类与委托类的解耦。
 * 缺点是只适用委托方法少的情况下, 试想一下如果委托类有几百上千个方法, 岂不是很难受, 要在代理类中写一堆的代理方法。这个需求动态代理可以搞定
 *
 */
public class HelloStaticProxy implements IHelloService {

    private Logger LOGGER = LoggerFactory.getLogger(HelloStaticProxy.class);

    private IHelloService helloService = new IHelloServiceImpl();

    @Override
    public String sayHello(String name) {

        /** 代理对象可以在此处包装一下*/
        LOGGER.debug("代理对象包装礼盒...");

        return helloService.sayHello(name);
    }

    @Override
    public String sayByeBye(String name) {
        return null;
    }
}
