package com.example.webservicetest.WebServicesConfig;

import com.example.webservicetest.Model.BoolResultModel;
import com.example.webservicetest.WebServicesConfig.ServiceConfig;
import com.example.webservicetest.WebServicesConfig.ServiceNames;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JackR on 2018/3/15.
 */

public class WebServicesUtil {

    /*
    * 发送webservice （相同服务命名空间，相同服务地址，相同服务类，不同服务名称）
    * 服务方法 method_name
    *  */
    public SoapObject SendWebServcie(String method_name,  Map<String, String> map){

        return SendWebService(ServiceConfig.NameSpace, ServiceConfig.ServiceUrl + ServiceNames.ServiceFile, method_name,
                ServiceConfig.NameSpace + method_name,  map);
    }

    /*
    * 发送webservice(相同服务命名空间，相同地址，不同服务类，不同服务名)
    * 服务文件名 service_file
    * 服务方法 method_name
    * */
    public SoapObject SendWebServcie(String service_file, String method_name, Map<String, String> map){

        return SendWebService(ServiceConfig.NameSpace, ServiceConfig.ServiceUrl + service_file, method_name,
                ServiceConfig.NameSpace + method_name,  map);
    }

    /*
    * 发送webservice(相同服务命名空间，不同服务地址，不同服务类，不同服务名)
    * 服务文件名 service_file
    * 服务方法 method_name
    * */
    public SoapObject SendWebServcie(String service_url, String service_file, String method_name, Map<String, String> map){

        return SendWebService(ServiceConfig.NameSpace, service_url + service_file, method_name,
                ServiceConfig.NameSpace + method_name,  map);
    }



    /*
    *  发送webService(不同服务命名空间，不同服务地址，不同服务类，不同服务名)
    * */
    public SoapObject SendWebService(String name_space, String service_url, String method_name, String soapAction,  Map<String, String> map){

        SoapObject soapObject = new SoapObject(name_space, method_name);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(service_url);
       if(map!=null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        try {
            httpTransportSE.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得服务返回的数据,并且开始解析
        SoapObject object = (SoapObject) envelope.bodyIn;
        return object;
    }
}
