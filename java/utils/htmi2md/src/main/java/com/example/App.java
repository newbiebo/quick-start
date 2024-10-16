package com.example;

import com.example.utils.JdkHttpUtils;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import org.apache.tika.Tika;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main(String[] args) {
        Tika tika = new Tika();
        String contentType = tika.detect("C:\\Users\\bowang28\\Desktop\\测试\\音频\\2018世界人工智能大会-雷军.mp3");
        System.out.println(contentType);
    }
//    public static void main( String[] args ) throws Exception{
//       startFetchFile(33753,"D:\\md");
//    }
//
//
//    public static void startFetchFile(int p,String path) throws Exception{
//        String urlStr = "https://cn.linux-console.net/?p="+p;
//        HttpURLConnection con = JdkHttpUtils.getOverSsl(new URL(urlStr));
//        Path pathSaved = Paths.get(path);
//        // 获取响应码
//        int responseCode = con.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//        // 检查响应码是否为200（成功）
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            // 读取响应内容
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder html = new StringBuilder();
//
//            // 将每一行响应内容添加到StringBuilder
//            while ((inputLine = in.readLine()) != null) {
//                html.append(inputLine);
//            }
//            in.close();
//
//            String htmlStr = html.toString();
////            System.out.println("====================================HTML=======================================");
////            System.out.println(htmlStr);
//            // 打印响应结果
//            String title = getTitle(htmlStr);
//            String mdStr = getHTML2md(htmlStr);
//            // 标记是否开始添加到结果的标志
//            boolean startWriting = false;
//            StringBuilder result = new StringBuilder();
//
//            // 将字符串按行分割
//            String[] lines = mdStr.split("\n");
//
//            //文件路径初始化
//            StringBuilder mdDir = new StringBuilder(pathSaved.toString()).append("/").append(title);
//            Path mdDirPath = Paths.get(mdDir.toString().replaceAll("\\s+", ""));
//            StringBuilder imgDir = new StringBuilder(mdDirPath.toString()).append("/").append("img");
//            StringBuilder mdFile = new StringBuilder(mdDirPath.toString()).append("/").append(title).append(".md");
//            new File(imgDir.toString()).mkdirs();
//
//            // 逐行处理
//            int i=0;
//            for (String line : lines) {
//                // 检查当前行是否包含关键字
//                if (line.contains("**网站搜索**")) {
//                    startWriting = true;
//                    continue;
//                }
//                if (line.contains("*** ** * ** ***")){
//                    i++;
//                    if (i>1) {
//                        result.append("*** ** * ** ***");
//                        break;
//                    }
//                }
//                if(line.contains("![](")){
//                    String url = line;
//                    // 定义正则表达式来匹配括号中的内容
//                    String regex = "!\\[.*?\\]\\((.*?)\\)";
//                    Pattern pattern = Pattern.compile(regex);
//                    Matcher matcher = pattern.matcher(url);
//
//                    String text="";
//                    // 检查是否找到匹配项
//                    if (matcher.find()) {
//                        // 获取括号中的文本内容
//                        text = matcher.group(1);
//                    } else {
//                        System.out.println("未找到匹配项。");
//                    }
//
//                    HttpURLConnection httpURLConnection = JdkHttpUtils.get(new URL("https://cn.linux-console.net/" + text));
//                    // 找到最后一个 '/' 的索引
//                    int lastSlashIndex = text.lastIndexOf('/');
//                    // 提取最后一个 '/' 之后的文本内容
//                    String lastSegment = text.substring(lastSlashIndex + 1);
//
//                    // 获取输入流
//                    InputStream inputStream = httpURLConnection.getInputStream();
//
//                    String imgPath = imgDir+"/"+lastSegment;
//                    // 创建输出流并将图片写入到本地文件
//                    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//                         FileOutputStream fileOutputStream = new FileOutputStream(imgPath)) {
//                        byte[] dataBuffer = new byte[1024];
//                        int bytesRead;
//                        while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
//                            fileOutputStream.write(dataBuffer, 0, bytesRead);
//                        }
//                    }
//
//                    System.out.println("图片下载成功: " + imgPath);
//
//                    line="![]("+"./img/"+lastSegment+")";
//                }
//                // 如果标志为true，则开始添加该行及其后的内容
//                if (startWriting) {
//                    result.append(line).append("\n");
//                }
//
//            }
//            System.out.println("==================================MD==========================================");
//
//            mdStr2file(result.toString(),Paths.get(mdFile.toString()));
//        } else {
//            System.out.println("GET请求失败");
//        }
//    }
//
//    public static String getHTML2md(String htmlStr) {
//        return FlexmarkHtmlConverter.builder().build().convert(htmlStr);
//    }
//
//    public static void mdStr2file(String mdStr, Path path) {
//        // 定义文件路径
//        String filePath = path.toString();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
//            writer.write(mdStr);
//            System.out.println("Markdown 文件已成功生成: " + filePath);
//        } catch (IOException e) {
//            System.err.println("写入文件时出错: " + e.getMessage());
//        }
//    }
//
//    private static String getTitle(String htmlContent){
//        // 定义正则表达式来匹配 <title> 标签内部内容
//        String regex = "<title>(.*?)</title>";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(htmlContent);
//        String titleContent;
//        // 查找匹配的内容
//        if (matcher.find()) {
//            titleContent = matcher.group(1);  // 获取匹配到的内容
//            System.out.println("Title 标签内部的内容是: " + titleContent);
//        } else {
//            titleContent= UUID.randomUUID().toString();
//            System.out.println("未找到 title 标签的内容");
//        }
//        return titleContent;
//    }

}
