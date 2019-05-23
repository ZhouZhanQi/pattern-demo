package com.pattern.demo;

import com.pattern.demo.example.CglibHelloExample;
import com.pattern.demo.interceptor.CglibInterceptor;
import com.pattern.demo.proxy.DynamicStaticProxy;
import com.pattern.demo.proxy.HelloStaticProxy;
import com.pattern.demo.service.IHelloService;
import com.pattern.demo.service.impl.IHelloServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatternDemoApplicationTests {

    @Test
    public void staticProxyTest() {
        HelloStaticProxy helloStaticProxy = new HelloStaticProxy();
        //静态代理类
        helloStaticProxy.sayHello("zzq");
    }



    @Test
    public void dynamicProxyTest() {
        DynamicStaticProxy dynamicStaticProxy = new DynamicStaticProxy(new IHelloServiceImpl());
        IHelloService helloService = (IHelloService) dynamicStaticProxy.newProxyInstance();
        helloService.sayByeBye("zzq1");
        helloService.sayHello("zzq2");
    }


    @Test
    public void cglibProxyTest() {
        CglibInterceptor cglibProxy = new CglibInterceptor();
        CglibHelloExample helloExample = (CglibHelloExample) cglibProxy.newProxyInstance(CglibHelloExample.class);
        helloExample.sayHello("zzq1");
        helloExample.sayByeBye("zzq2");
    }

}
