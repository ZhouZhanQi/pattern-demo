package com.pattern.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc cglib 代理类
 *
 * CGLIB 代理是针对类来实现代理的，原理是对指定的委托类生成一个子类并重写其中业务方法来实现代理。代理类对象是由 Enhancer 类创建的。CGLIB 创建动态代理类的模式是:
 *
 * 查找目标类上的所有非 final 的 public 类型的方法 (final 的不能被重写)
 *
 * 将这些方法的定义转成字节码
 *
 * 将组成的字节码转换成相应的代理的 Class 对象然后通过反射获得代理类的实例对象
 *
 * 实现 MethodInterceptor 接口, 用来处理对代理类上所有方法的请求
 */
public class CglibInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibInterceptor.class);

    /**
     * CGLIB 增强类对象，代理类对象是由 Enhancer 类创建的，
     * Enhancer 是 CGLIB 的字节码增强器，可以很方便的对类进行拓展
     */
    private Enhancer enhancer = new Enhancer();


    /**
     *
     * @param obj 被代理的对象
     * @param method 代理的方法
     * @param objects 方法的参数
     * @param methodProxy cglib方法代理对象
     * @return cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        LOGGER.debug("方法调用之前");
        Object o = methodProxy.invokeSuper(obj, objects);
        LOGGER.debug("方法调用之后");
        return o;
    }

    /**
     * 使用动态代理创建一个代理对象
     * @param c
     * @return
     */
    public  Object newProxyInstance(Class<?> c) {
        /**
         * 设置产生的代理对象的父类,增强类型
         */
        enhancer.setSuperclass(c);
        /**
         * 定义代理逻辑对象为当前对象，要求当前对象实现 MethodInterceptor 接口
         */
        enhancer.setCallback(this);
        /**
         * 使用默认无参数的构造函数创建目标对象,这是一个前提,被代理的类要提供无参构造方法
         */
        return enhancer.create();
    }
}
