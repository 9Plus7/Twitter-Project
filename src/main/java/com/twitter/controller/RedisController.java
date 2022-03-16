package com.twitter.controller;

import com.twitter.utils.RedisUtil;
import com.twitter.utils.StateParameter;
import com.twitter.controller.BaseController;
import com.twitter.utils.RedisConstants;
import com.twitter.model.Customer;
import com.twitter.utils.SerializeUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/redis")
public class RedisController extends BaseController {

    @Autowired
    RedisUtil redisUtil;

    /**
     * @auther: zhangyingqi
     * @date: 16:23 2018/8/29
     * @param: []
     * @return: org.springframework.ui.ModelMap
     * @Description: 执行redis写/读/生命周期
     */
    @RequestMapping(value = "getRedis",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getRedis(){
        redisUtil.set("20182018","这是一条测试数据", RedisConstants.datebase1);
        Long resExpire = redisUtil.expire("20182018", 60, RedisConstants.datebase1);//设置key过期时间
        logger.info("resExpire="+resExpire);
        String res = redisUtil.get("20182018", RedisConstants.datebase1);

        //测试Redis保存对象
        Customer c = new Customer();
        c.setId(24);
        c.setFirstName("冯绍峰");
        redisUtil.set("20181017".getBytes(), SerializeUtil.serialize(c),RedisConstants.datebase1);
        byte[] user = redisUtil.get("20181017".getBytes(),RedisConstants.datebase1);
        Customer us = (Customer) SerializeUtil.unserialize(user);
        System.out.println("user="+us.toString());

        return getModelMap(StateParameter.SUCCESS, res, "执行成功");
    }
}
