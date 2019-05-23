package com.pattern.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author zhouzq
 * @date 2019/5/23
 * @desc mockito测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockitoTest.class);

    @Test
    public void conductVerificationTest () {

        // mock creation 创建mock对象
        List mockList = mock(List.class);

        //using mock object 使用mock对象
        mockList.add("one");
        mockList.clear();

        //verification 验证
        verify(mockList).add("one");
        verify(mockList).clear();
    }


    @Test
    public void stubbingTest () {
        // mock creation 创建mock对象
        LinkedList mockLinkList = mock(LinkedList.class);

        //stubbing
        // 测试桩
        when(mockLinkList.get(0)).thenReturn("first");
        when(mockLinkList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        // 输出“first”
        LOGGER.debug("0: {}", mockLinkList.get(0).toString());

        //following throws runtime exception
        // 抛出异常
        try {
            LOGGER.debug("1: {}", mockLinkList.get(1).toString());
        } catch (Exception e) {
            LOGGER.error("mockLinkList get(1) error {}", e.getMessage());
        }

        // 因为get(999) 没有打桩，因此输出null
        LOGGER.debug("999: {}", mockLinkList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        // 验证get(0)被调用的次数
        verify(mockLinkList).get(0);

    }


    @Test
    public void parameterMatchTest () {
        LinkedList mockLinkList = mock(LinkedList.class);

        //stubbing using built-in anyInt() argument matcher
        // 使用内置的anyInt()参数匹配器
        when(mockLinkList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        // 使用自定义的参数匹配器( 在isValid()函数中返回你自己的匹配器实现 )
        when(mockLinkList.contains(argThat(new IsValid()))).thenReturn(true);

        //following prints "element"
        // 输出element
        LOGGER.debug("999：{}", mockLinkList.get(999));

        //you can also verify using an argument matcher
        // 你也可以验证参数匹配器
        verify(mockLinkList).get(anyInt());

        TestMatch testMatch = mock(TestMatch.class);
        verify(testMatch).someMethod(anyInt(), anyString(), eq("third argument"));
//        verify(testMatch).someMethod(anyInt(), anyString(), "third argument");

    }





    @Test
    public void countMatchTest () {

        List mockList = mock(List.class);


        mockList.add("once");

        mockList.add("twice");
        mockList.add("twice");

        mockList.add("three times");
        mockList.add("three times");
        mockList.add("three times");


        verify(mockList).add("once");

        verify(mockList, times(1)).add("once");
        verify(mockList, times(2)).add("twice");
        verify(mockList, times(3)).add("three times");


        verify(mockList, never()).add("never append");

        verify(mockList, atLeastOnce()).add("three times");
        verify(mockList, atLeast(2)).add("three times");
        verify(mockList, atMost(5)).add("three times");
    }



    @Test
    public void noReturnMethodTest () {

        List mockList = mock(List.class);
        doThrow(new RuntimeException()).when(mockList).clear();

        try {
            mockList.clear();
        } catch (Exception e) {
            LOGGER.error("no return method error {}", e.getMessage());
        }
    }


    @Test
    public void  executionSequence () {

        List singleList = mock(List.class);

        singleList.add("first add");

        singleList.add("second add");

        InOrder inOrder = inOrder(singleList);

        inOrder.verify(singleList).add("first add");
        inOrder.verify(singleList).add("second add");

    }




    private class IsValid implements ArgumentMatcher<List> {

        @Override
        public boolean matches(List list) {
            return list.contains(1) || list.contains(2);
        }
    }


    private class TestMatch {
        public <T> T someMethod (int a, String b, T c) {
            return null;
        }
    }
}
