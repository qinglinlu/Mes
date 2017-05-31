package com.mes.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by monkey on 2017/5/27.
 */

public class ResponseData implements ISoapService {
    private String serverAdress;
    /*和WCF ServiceContract中的Namespace一致*/
    private static final String NameSpace = "http://www.mes.com";
    /*WCF在iis中的调用路径(http://服务器/虚拟目录/服务)*/
//    private static final String URL = "http://192.168.1.20:49247/Service1.svc";
    private String URL;
    /*Namespace/服务接口/方法*/
//    private  String SOAP_ACTION = "http://www.master.haku/HelloService/SayHello";
    private String SOAP_ACTION;
    /*方法名*/
    private String MethodName;
    private String command;
    private String canshu;
    private Context context;

    public ResponseData(String command, String parm) {
        this.context = MyApplication.getContext();
        this.command = command;
        this.canshu = parm;
        //初始化Wcf参数
        serverAdress = getServerAddress();
        URL = serverAdress + "Service1.svc";
        MethodName = command;
        SOAP_ACTION = NameSpace + "/MesService/" + command;

//        Log.d("loginactivity","服务器地址："+serverAdress);
//        Log.d("loginactivity","网址："+URL);
//        Log.d("loginactivity","方法名称："+MethodName);
//        Log.d("loginactivity","ACTION:"+SOAP_ACTION);

    }

    private String getServerAddress() {
        SharedPreferences pref = context.getSharedPreferences("server", Context.MODE_PRIVATE);
        return pref.getString("serveraddress", "");
    }

    @Override
    public SoapObject LoadResult() {

        SoapObject soapObject = new SoapObject(NameSpace, MethodName);
        try {
            JSONArray jsonArray = new JSONArray(canshu);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("loginactivity",jsonObject.getString("name"));
//                Log.d("loginactivity",jsonObject.getString("value"));
                soapObject.addProperty(jsonObject.getString("name"), jsonObject.getString("value"));//传参，记住参数名必须和WCF方法中的参数名一致
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // 版本
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE trans = new HttpTransportSE(URL);
        trans.debug = true; // 使用调试功能

        try {
            trans.call(SOAP_ACTION, envelope);
//            Log.d("Response","执行完成");
//            System.out.println("Call Successful!");
        } catch (IOException e) {
            Log.d("Response", "有错误发生，IOException:" + e.getMessage());
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.d("Response", "有错误发生，XmlPullParserException:" + e.getMessage());
            e.printStackTrace();
        }
        SoapObject result = (SoapObject) envelope.bodyIn;

        return result;
    }
}
