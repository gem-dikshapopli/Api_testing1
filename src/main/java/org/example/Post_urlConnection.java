package org.example;

import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Post_urlConnection {
    public static void main(String[] args){
        int id = 0;//to use the id for put method
        try {
            URL url = new URL("https://restful-booker.herokuapp.com/booking");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", "abc");
            jsonObject.put("lastname", "def");
            jsonObject.put("totalprice", 112);
            jsonObject.put("depositpaid", true);
            JSONObject childObject = new JSONObject();
            childObject.put("checkin", "2018-03-02");
            childObject.put("checkout", "2019-03-02");
            jsonObject.put("bookingdates", childObject);
            jsonObject.put("additionalneeds", "Dinner");

            //------------to write in the server------------------
            try (OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write(jsonObject.toString().getBytes());
                outputStream.flush();
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] ar = line.split(",");
                        String arr[] = ar[0].split(":");
                        id = Integer.parseInt(arr[1]);
                        System.out.println(line);
                    }
                    conn.disconnect();
                }
            } else {
                System.out.println(conn.getResponseCode());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        //---------- For authentication I am using PUT method--------------//


        System.out.println("-------------------Changed Values------------------");
        try {
            URL url = new URL("https://restful-booker.herokuapp.com/booking/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            //Authorization required
            connection.setRequestProperty("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=");
            connection.setDoOutput(true);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", "changed");
            jsonObject.put("lastname", "change");
            jsonObject.put("totalprice", 115);
            jsonObject.put("depositpaid", true);
            JSONObject childObject = new JSONObject();
            childObject.put("checkin", "2020-03-02");
            childObject.put("checkout", "2023-03-02");
            jsonObject.put("bookingdates", childObject);
            jsonObject.put("additionalneeds", "Brunch");


            //          Fetching authentication token
            String basicAuth = "Authentication : " + new String(Base64.getEncoder().encode(jsonObject.toString().getBytes()));
            System.out.println(basicAuth);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonObject.toString().getBytes());
                os.flush();
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String str;
                    while ((str = br.readLine()) != null) {
                        System.out.println(str);
                    }
                }
                connection.disconnect();
            } else {
                System.out.println(connection.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
