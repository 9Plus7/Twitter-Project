package com.twitter.utils;
public class RedisConstants {

    public static final String spilt=":";

    /**
     * redis库1  保存档案树
     */
    public static final Integer datebase1=1;

    /**
     * 1.redis库2 保存档案表格
     * 2.保存分页码
     */
    public static final Integer datebase2=2;

    /**
     * redis库3 保存档案image url
     */
    public static final Integer datebase3=3;


    /**
     * redis库4 记录重发次数
     */
    public static final Integer datebase4=4;

    /**
     * redis库5 记录任务参数
     */
    public static final Integer datebase5=5;


    public RedisConstants() {

    }
}