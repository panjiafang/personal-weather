package com.rqpw.weather.network;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Pan Jiafang on 2014/7/14.
 */
public class NetWorkUtil {
    public static int TimeOut = 12*1000;
    /**
     * @author Pan Jiafang
     * @date 2013-3-21
     * @desc 服务器请求
     */
    private static String Conn2Server(String url){
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, TimeOut);
        HttpConnectionParams.setSoTimeout(params, TimeOut);
        HttpClient client = new DefaultHttpClient(params);

        HttpPost post;
            post = new HttpPost(url);
        post.setHeader("Content-type","application/json; charset=utf-8");

        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity());

                System.out.println(result);
                return result;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(client != null){
                client.getConnectionManager().shutdown();
            }
        }
        return null;
    }

    public static String getWeatherInfo(String q){
        String url = "http://api.worldweatheronline.com/free/v1/weather.ashx?q="+q+"&format=json&extra=localObsTime&num_of_days=5&key=ca88095da564000cba0ba6d0356dcd0c8cecc061";
        return Conn2Server(url);
    }

    public static String getLocation(String q){
        String url = "http://api.worldweatheronline.com/free/v1/search.ashx?q="+q+"&format=json&key=ca88095da564000cba0ba6d0356dcd0c8cecc061";
        return Conn2Server(url);
    }
}
