package com.lemon.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: class_26_base
 * @Site: http://www.lemonban.com
 * @Forum: http://testingpai.com
 * @Copyright: ©2020 版权所有 湖南省零檬信息技术有限公司
 * @Author: odin
 * @Create: 2021-07-26 16:07
 * @Desc： 环境变量
 **/

public class Environment {
    //环境变量
/*    public static String token;
    public static int memberId;*/
    public static Map<String,Object> envData = new HashMap<String, Object>();     //设计成map，后续不管有多少变量都能保存到map里面去
}
