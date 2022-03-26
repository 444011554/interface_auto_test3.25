package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.ExcelPojo;
import com.lemon.util.PhoneRandomUtil;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
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
 * @Create: 2021-08-17 11:17
 * @Desc：
 **/

public class AddloanTest extends BaseTest {
    @BeforeClass
    public void setup(){
        //生成两个角色的随机手机号码（借款人+管理员）
        String borrowserPhone = PhoneRandomUtil.getUnregisterPhone();
        String adminPhone = PhoneRandomUtil.getUnregisterPhone();
        Environment.envData.put("borrower_phone",borrowserPhone);
        Environment.envData.put("admin_phone",adminPhone);
        //读取用例数据-前面4条
        List<ExcelPojo> list = readSpecifyExcelData(5,0,4);
        for (int i=0; i<list.size();i++){
            ExcelPojo excelPojo = list.get(i);           //分别取出前4条用例保存至excelPojo里
            //参数替换
            excelPojo = casesReplace(excelPojo);
            //发送请求
            Response res = request(excelPojo,"加贷模块");
            //判断是否要提取响应数据
            if (excelPojo.getExtract() != null){                          //如果提取响应数据不为空，
                extractToEnvironment(excelPojo,res);                      //就提取并保存至环境变量里
            }
        }
    }

    @Test(dataProvider = "getAddLoanDatas")
    public void testAddLoan(ExcelPojo excelPojo){
        //参数替换
        excelPojo = casesReplace(excelPojo);
        //发送加标请求
        Response res = request(excelPojo,"加贷模块");
        //响应断言
        assertResponse(excelPojo,res);
    }

    @DataProvider
    public Object[] getAddLoanDatas(){
        List<ExcelPojo> listDatas = readSpecifyExcelData(5,4);      //读取第5张表，第5条用例及以下
        //把集合转换为一个一维数组
        return listDatas.toArray();
    }

    @AfterTest
    public void teardown(){

    }
}
