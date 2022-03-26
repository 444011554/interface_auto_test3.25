package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
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
 * @Copyright: ©2020 版权所有 湖南省零檬信息技术有限公司
 * @Author: odin
 * @Create: 2021-07-23 15:36
 * @Desc：
 **/

public class LoginTest extends BaseTest {
    @BeforeClass
    public void setup(){
        //生成一个没有被注册过的手机号码
        String phone = PhoneRandomUtil.getUnregisterPhone();
        //将注册用例参数里面的phone保存到环境变量里面去
        Environment.envData.put("phone",phone);        //"phone"必须跟注册用例里面的{{phone}}参数保持一致
        //前置条件
        //读取Excel里面的第一条数据-执行-生成一条注册过了的手机号码
        List<ExcelPojo> listDatas = readSpecifyExcelData(2,0,1);     //读取第2张Excel表，从第1行开始（默认从0开始），读取1行
        ExcelPojo excelPojo = listDatas.get(0);     //listDatas.get(0)取出1条用例，保存至excelPojo里
        //替换
        excelPojo = casesReplace(excelPojo);
        //执行注册接口请求
        Response res = request(excelPojo,"登录模块");
        //提取注册返回的手机号码保存存到环境变量中
        extractToEnvironment(excelPojo,res);
    }

    @Test(dataProvider = "getLoginDatas")
    public void login(ExcelPojo excelPojo) {
        //替换用例数据
        excelPojo = casesReplace(excelPojo);
        //发起登录请求
        Response res = request(excelPojo,"登录模块");
        //响应断言
        assertResponse(excelPojo,res);
    }


    @DataProvider
    public Object [] getLoginDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData(2,1);      //读取第2张Excel表，从第2行开始直到结束
        //把集合转换为一个一维数组
        return listDatas.toArray();
    }

}
