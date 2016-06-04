package com.xuyihao.test;

import com.xuyihao.url.connectors.HttpUtil;
import com.xuyihao.url.enums.Platform;

import java.util.HashMap;

/**
 * Created by johnson on 16-6-4.
 * @author johnson
 * @description for testing socket capture
 */
public class TestMain {
    public static void main(String[] args){
        HttpUtil httpUtil = new HttpUtil(Platform.LINUX);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("Hello", "Hello! Johnson!");
        parameters.put("Word", "Johnson I love you!");
        httpUtil.executePostByMultipart("http://127.0.0.1:8093", parameters);
    }
}
