package com.example.string;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class StringTest {

    @Test
    public void aaa(){
        String url = "http://www.baidu.com";
        //是否以xxx开始
        System.out.println("startsWith(): "+url.startsWith("http"));
        //是否从索引位1以xxx开始
        System.out.println("startsWith(): "+url.startsWith("ttp",1));
        //替换
        System.out.println("replace(): "+url.replace("http","ws"));
        //是否匹配，入参正则表达式
        String INVALID_CHAR_PATTERN = "[\\\\/:*?\"<>|]";
        System.out.println("matches(): "+url.matches(".*" + INVALID_CHAR_PATTERN + ".*"));
        //转小写
        System.out.println("toLowerCase(Locale.ROOT): "+url.toLowerCase(Locale.ROOT));
        //转大写
        System.out.println("toUpperCase(Locale.ROOT): "+url.toUpperCase(Locale.ROOT));
        //string->bytes[]
        System.out.println("getBytes(): "+ Arrays.toString(url.getBytes()));
        //拼接
        System.out.println("concat(): "+ url.concat("ba"));
        //去除前后空格
        System.out.println("trim(): "+ url.trim());
        //截取第一个/之前的内容
        System.out.println("trim(): "+ url.substring(0,url.indexOf("/")));
        //截取第一个/之后的内容
        System.out.println("trim(): "+ url.substring(url.indexOf("/")+1));
        //截取最后一个/之前的内容
        System.out.println("trim(): "+ url.substring(0,url.lastIndexOf("/")));
        ArrayList<String> strings = new ArrayList<>();
//        strings.add("hello");
//        strings.add("world");
        strings.add("");
//        strings.remove("");
        System.out.println("join(): "+ strings);
        String[] split = "".split("[,，]");
        System.out.println("split(): "+ Arrays.toString(split));
    }
}
