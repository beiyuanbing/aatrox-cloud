package com.aatrox.common.utils;

/**
 * @desc
 * @auth beiyuanbing
 * @date 2019-05-06 10:42
 **/

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName:判断是否为工作日
 * Created by : byb
 * Date ：2018-05-06
 * Description:
 */
public class HolidayUtil {
    /**
     * :请求接口
     *
     * @param httpArg :参数
     * @return 返回结果
     */
    private static String requestHolidyServer(String httpArg) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("date", httpArg);
        String host = "http://api.goseek.cn/Tools/holiday";
        String path = "";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        try {
            HttpResponse response = HttpUtils.doGet(host, "", headers, paramsMap);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("return：" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否是工作日
     *
     * @param dayString yyyyMMdd格式的日期
     * @return
     */
    public static boolean isWorkDay(String dayString) {
        String result = requestHolidyServer(dayString);
        if (StringUtils.isEmpty(result)) {
            return true;
        }
        /**不为空的判断**/
        JSONObject resultJson = JSONObject.parseObject(result);
        //0 上班 1周末 2节假日{"code":10000,"data":0}
        if (resultJson.getIntValue("data") == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否是工作日
     *
     * @param date 日期
     * @return
     */
    public static boolean isWorkDay(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String httpArg = f.format(date);
        return isWorkDay(httpArg);

    }


    public static void main(String[] args) {
        //判断今天是否是工作日 周末 还是节假日
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String httpArg = f.format(new Date());
        //httpArg="20171001";
        System.out.println(httpArg);
        System.out.println(isWorkDay(httpArg));

        //0 上班 1周末 2节假日
    }
}