package org.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import java.io.IOException;

public class PostMethod {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient request= HttpClients.createDefault();
        HttpPost post=new HttpPost("https://restful-booker.herokuapp.com/booking");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Accept","application/json");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("firstname","Diksha");
        jsonObject.put("lastname","Popli");
        jsonObject.put("totalprice",112);
        jsonObject.put("depositpaid",true);
        JSONObject childObject=new JSONObject();
        childObject.put("checkin","2018-02-02");
        childObject.put("checkout","2019-02-02");
        jsonObject.put("bookingdates",childObject);
        jsonObject.put("additionalneeds","Lunch");
        post.setEntity(new StringEntity(jsonObject.toString()));
        CloseableHttpResponse response= request.execute(post);
        System.out.println(response.getStatusLine().getStatusCode());
        String result= EntityUtils.toString(response.getEntity());
        System.out.println(result);
    }
}
