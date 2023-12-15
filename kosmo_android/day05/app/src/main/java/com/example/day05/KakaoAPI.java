package com.example.day05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class KakaoAPI {
    public static String connect(String apiURL) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK d98342bfb3d10bd8a8d18f10982fe1c8");
            int responseCode = con.getResponseCode();
            BufferedReader br;

            if(responseCode==200) {  // 정상 호출인 경우
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {  //에러 발생가 발생한 경우
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println("결과..............." + response.toString());
            return response.toString();
        }catch (Exception e) {
            System.out.println("에러............" + e.toString());
            return e.toString();
        }
    }
}
