package com.pattern.demo.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc 动态代理
 *
 * 在 Java 的动态代理中, 主要涉及 2 个类,java.lang.reflect.Proxy和java.lang.reflect.InvocationHandler
 * 我们需要一个实现 InvocationHandler 接口的中间类, 这个接口只有一个方法 invoke 方法, 方法的每个参数的注释如下代码。
 *
 * 我们对处理类中的所有方法的调用都会变成对 invoke 方法的调用，这样我们可以在 invoke 方法中添加统一的处理逻辑（也可以根据 method 参数判断是哪个方法）。
 * 中间类 (实现了 InvocationHandler 的类) 有一个委托类对象引用, 在 Invoke 方法中调用了委托类对象的相应方法，通过这种聚合的方式持有委托类对象引用，
 * 把外部对 invoke 的调用最终都转为对委托类对象的调用。
 *
 * 实际上，中间类与委托类构成了静态代理关系，在这个关系中，中间类是代理类，委托类是委托类。然后代理类与中间类也构成一个静态代理关系，
 * 在这个关系中，中间类是委托类，代理类是代理类。也就是说，动态代理关系由两组静态代理关系组成，这就是动态代理的原理。
 *
 */
public class DynamicStaticProxy implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicStaticProxy.class);

    private Object object;

    public DynamicStaticProxy(Object object) {
        this.object = object;
    }


    /**
     * 动态生成代理类对象,Proxy.newProxyInstance
     * @return 返回代理类的实例
     */
    public Object newProxyInstance() {
        return Proxy.newProxyInstance(
                //指定代理对象的类加载器
                object.getClass().getClassLoader(),
                //代理对象需要实现的接口，可以同时指定多个接口
                object.getClass().getInterfaces(),
                //方法调用的实际处理者，代理对象的方法调用都会转发到这里
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.debug("invoke before");
        Object result = method.invoke(object, args);
        LOGGER.debug("invoke after");
        return result;
    }
}
