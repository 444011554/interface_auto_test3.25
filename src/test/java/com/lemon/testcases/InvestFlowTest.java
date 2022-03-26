package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.PhoneRandomUtil;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Project: class_26_base
 * @Site: http://www.lemonban.com
 * @Forum: http://testingpai.com
 * @Copyright: ©2020 版权所有 湖南省零檬信息技术有限公司
 * @Author: odin
 * @Create: 2021-08-10 11:04
 * @Desc：
 **/

public class InvestFlowTest extends BaseTest {
    @BeforeClass
    public void setup(){
        //生成三个角色的随机手机号码（投资人+借款人+管理员）
        String borrowserPhone = PhoneRandomUtil.getUnregisterPhone();
        String adminPhone = PhoneRandomUtil.getUnregisterPhone();
        String investPhone = PhoneRandomUtil.getUnregisterPhone();
        //将参数保存至环境变量中
        Environment.envData.put("borrower_phone",borrowserPhone);     //borrowserPhone是变量命名，borrower_phone是参数名
        Environment.envData.put("admin_phone",adminPhone);
        Environment.envData.put("invest_phone",investPhone);
        //读取用例数据从第一条至第九条
        List<ExcelPojo> list = readSpecifyExcelData(4,0,9);
        for (int i=0; i<list.size(); i++){
            //保存
            ExcelPojo excelPojo = list.get(i);
            //替换
            excelPojo = casesReplace(excelPojo);
            //发送请求
            Response res = request(excelPojo,"业务流模块");
            //判断是否要提取响应数据
            if (excelPojo.getExtract() != null){                     //如果提取响应数据不为空，
                extractToEnvironment(excelPojo,res);                 //就提取并保存至环境变量里
            }
        }

    }

    @Test
    public void testInvest(){
        //读取第10行及10行之后的用例，因为用例只有1行
        List<ExcelPojo> list = readSpecifyExcelData(4,9);
        ExcelPojo excelPojo = list.get(0);       //list.get(0)表示取出来一条用例，取到之后保存到excelPojo里面
        //参数替换
        excelPojo = casesReplace(excelPojo);
        //发送投资请求
        Response res = request(excelPojo,"业务流模块");
        //响应断言
        assertResponse(excelPojo,res);
        //数据库断言
        assertSQL(excelPojo);

    }

    @AfterTest
    public void teardown(){

    }
}
