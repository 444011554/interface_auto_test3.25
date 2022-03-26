package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
//import com.lemon.encryption.RSAManager;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.PhoneRandomUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Project: class_26_base
 * @Site: http://www.lemonban.com
 * @Forum: http://testingpai.com
 * @Author: odin
 * @Create: 2022-01-20 11:55
 * @Desc： v2
 **/

public class RechargeTestV2 extends BaseTest {
    int memberId;
    String token;

    @BeforeClass
    public void setup(){
        //生成一个没有被注册过的手机号码
        String phone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envData.put("phone",phone);
        //前置条件
        //读取Excel里面前两条数据
        List<ExcelPojo> listDatas = readSpecifyExcelData(3,0,2);
        ExcelPojo excelPojo = listDatas.get(0);               //listDatas.get(0)取出第1条用例，保存至excelPojo里
        //参数替换
        excelPojo = casesReplace(excelPojo);
        //注册请求
        Response resRegister = request(excelPojo,"充值模块");
        //【提取返回数据(extract)】的字段保存到环境变量中
        extractToEnvironment(excelPojo,resRegister);            //extractToEnvironment：提取并保存至环境变量中
        //参数替换，替换{{phone}}----注册的返回值phone替换登录的请求参数
        casesReplace(listDatas.get(1));                //替换listDatas.get(1)第2条用例的参数
        //登录请求
        Response resLogin = request(listDatas.get(1),"充值模块");          //发起第2条用例请求
        //获取登录【提取返回数据】member_id和token并保存到环境变量中去
        extractToEnvironment(listDatas.get(1),resLogin);       // extractToEnvironment(接口原始参数,接口返回值)；

    }

    @Test(dataProvider = "getRechargeDatas")
    public void testRecharge(ExcelPojo excelPojo) {
        //参数替换，替换{{token}}和{{member_id}}
        excelPojo = casesReplace(excelPojo);           //casesReplace本质是通过正则表达式来替换的
        //充值请求
        Response res = request(excelPojo,"充值模块");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSQL(excelPojo);

    }

    @DataProvider
    public Object[] getRechargeDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData(3,2);
        return listDatas.toArray();         //将list集合转化为一维数组
    }

}
