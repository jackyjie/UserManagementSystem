package com.example.webservicetest.Business;

import com.example.webservicetest.Model.BoolResultModel;
import com.example.webservicetest.Model.ListResultModel;
import com.example.webservicetest.Model.ObjectResultModel;
import com.example.webservicetest.UserModel;
import com.example.webservicetest.WebServicesConfig.ServiceNames;
import com.example.webservicetest.WebServicesConfig.WebServicesUtil;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JackR on 2018/3/15.
 */

public class ServiceCollection {

    /*
    * 获取用户信息
    * */
    public static ObjectResultModel<UserModel> GetUser(int id){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", Integer.toString(id));
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.Get;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        ObjectResultModel<UserModel> model= new ObjectResultModel<UserModel>();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                SoapObject data = ((SoapObject) detail.getProperty("Data"));
                UserModel user_model = new UserModel();
                user_model.setUserId(Integer.parseInt(data.getProperty("userId").toString()));
                user_model.setPassword(data.getProperty("password").toString());
                user_model.setTelPhone(data.getProperty("telphone").toString());
                user_model.setUserName(data.getProperty("userName").toString());
                model.Data = user_model;
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }

    /*
   * 获取用户列表
   * */
    public static ListResultModel<UserModel> GetUserList(){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.GetList;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        ListResultModel<UserModel> model= new ListResultModel<UserModel>();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                SoapObject data = ((SoapObject) detail.getProperty("Data"));
                    List<UserModel> list = new ArrayList<UserModel>();
                    for(int i = 0 ; i< data.getPropertyCount();i++){
                        UserModel user_model = new UserModel();
                        SoapObject item = (SoapObject)data.getProperty(i);
                        user_model.setUserId(Integer.parseInt(item.getProperty("userId").toString()));
                        user_model.setPassword(item.getProperty("password").toString());
                        user_model.setTelPhone(item.getProperty("telphone").toString());
                        user_model.setUserName(item.getProperty("userName").toString());
                        list.add(user_model);
                }
                model.Data = list;
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }

    /*
   * Login
   * */
    public static BoolResultModel Login(int id, String pwd){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", Integer.toString(id));
        map.put("password", pwd);
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.Login;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        BoolResultModel model= new BoolResultModel();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }

    /*
   * 新增用户
   * */
    public static BoolResultModel AddUser(String userName, String telphone){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("telphone", telphone);
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.Add;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        BoolResultModel model= new BoolResultModel();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }

    /*
   * 更新用户信息
   * */
    public static BoolResultModel UpdateUser(int id, String userName, String telphone){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", Integer.toString(id));
        map.put("userName", userName);
        map.put("telphone", telphone);
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.Update;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        BoolResultModel model= new BoolResultModel();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }

    /*
   * 删除
   * */
    public static BoolResultModel DeleteUser(int id){
        // 发送信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", Integer.toString(id));
        WebServicesUtil util = new WebServicesUtil();
        String MethodName = ServiceNames.Delete;
        SoapObject result = util.SendWebServcie(MethodName,map);

        // 处理返回
        BoolResultModel model= new BoolResultModel();
        if (result != null) {
            SoapObject detail = (SoapObject)result.getProperty(MethodName +"Result");
            String return_flag = detail.getProperty("Flag").toString();
            String return_message = detail.getProperty("Message").toString();
            if(return_flag.equals("true")){
                model.Flag = true;
                model.Message = "返回成功";
            }
            else {
                model.Flag = false;
                model.Message = return_message;
            }
        }
        else {
            model.Flag = false;
            model.Message = "未获取远程数据";
        }
        return model;
    }
}
