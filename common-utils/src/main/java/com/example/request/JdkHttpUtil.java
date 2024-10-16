package com.example.request;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class JdkHttpUtil {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static HttpURLConnection getOverSsl(URL url)throws Exception{
        overSsl();
        return get(url);
    }
    public static HttpURLConnection get(URL url)throws Exception{
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // 设置请求方法为GET
        con.setRequestMethod("GET");
        // 设置请求头
        con.setRequestProperty("User-Agent", USER_AGENT);
        return con;
    }
    private static void overSsl() throws Exception{
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
    }
}
