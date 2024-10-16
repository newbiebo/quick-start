package com.example;

import com.example.request.JdkHttpUtil;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 20041; i <= 33753; i++) {
            int finalI = i;
            executorService.submit(()->{
                        addLog("任务开始：第"+finalI+"次任务","D:\\md\\thread_executor.log");
                        System.out.println("任务开始：第"+finalI+"次任务");
                        try {
                            startFetchFile(finalI,"D:\\md");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
            Thread.sleep(500);
            addLog("任务结束：第"+finalI+"次任务","D:\\md\\thread_executor.log");
        }
        executorService.shutdown();
    }
    public static void startFetchFile(int p,String path) throws Exception{
        String urlStr = "https://cn.linux-console.net/?p="+p;
        HttpURLConnection con = JdkHttpUtil.getOverSsl(new URL(urlStr));
        Path pathSaved = Paths.get(path);
        // 获取响应码
        int responseCode = con.getResponseCode();
        addLog("Response Code: [" + responseCode+"] "+"targetUrl: "+urlStr,"D:\\md\\request_status.log");
        // 检查响应码是否为200（成功）
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder html = new StringBuilder();

            // 将每一行响应内容添加到StringBuilder
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();

            String htmlStr = html.toString();
            // 打印响应结果
            String title = getTitle(htmlStr);
            String mdStr = getHTML2md(htmlStr);
            // 标记是否开始添加到结果的标志
            boolean startWriting = false;
            StringBuilder result = new StringBuilder();

            // 将字符串按行分割
            String[] lines = mdStr.split("\n");

            //文件路径初始化
            int page = 100;

            if (p <=200 && p >100) {
                page = 200;
            }
            if (p<=300 && p >200) {
                page = 300;
            }
            if (p <= 5000 && p >300) {
                page = 5000;
            }
            if (p<=10000 && p >5000) {
                page = 10000;
            }
            if (p<=15000 && p >10000) {
                page = 15000;
            }
            if (p<=20000 && p >15000) {
                page = 20000;
            }
            if (p<=25000 && p >2000) {
                page = 25000;
            }
            if (p<=30000 && p >25000) {
                page = 30000;
            }
            if (p<=35000 && p >30000) {
                page = 35000;
            }
            StringBuilder mdDir = new StringBuilder(pathSaved.toString()).append("/").append(page+"").append("/").append(title);
            Path mdDirPath = Paths.get(mdDir.toString().replaceAll("\\s+", ""));
            StringBuilder imgDir = new StringBuilder(mdDirPath.toString()).append("/").append("img");
            StringBuilder mdFile = new StringBuilder(mdDirPath.toString()).append("/").append(title).append(".md");
            new File(imgDir.toString()).mkdirs();

            // 逐行处理
            int i=0;
            for (String line : lines) {
                // 检查当前行是否包含关键字
                if (line.contains("**网站搜索**")) {
                    startWriting = true;
                    continue;
                }
                if (line.contains("*** ** * ** ***")){
                    i++;
                    if (i>1) {
                        result.append("*** ** * ** ***");
                        break;
                    }
                }
                if(line.contains("![](")){
                    String url = line;
                    // 定义正则表达式来匹配括号中的内容
                    String regex = "!\\[.*?\\]\\((.*?)\\)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(url);
                    String text="";
                    // 检查是否找到匹配项
                    if (matcher.find()) {
                        // 获取括号中的文本内容
                        text = matcher.group(1);
                    } else {
                        addLog("未找到匹配项，p： "+p,"D:\\md\\matcher_error.log");
                    }

                    HttpURLConnection httpURLConnection = JdkHttpUtil.get(new URL("https://cn.linux-console.net/" + text));
                    // 找到最后一个 '/' 的索引
                    int lastSlashIndex = text.lastIndexOf('/');
                    // 提取最后一个 '/' 之后的文本内容
                    String lastSegment = text.substring(lastSlashIndex + 1);
                    lastSegment = lastSegment.replaceAll("/", "或");

                    // 获取输入流
                    InputStream inputStream = httpURLConnection.getInputStream();

                    String imgPath = imgDir+"/"+lastSegment;
                    // 创建输出流并将图片写入到本地文件
                    try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                         FileOutputStream fileOutputStream = new FileOutputStream(imgPath)) {
                        byte[] dataBuffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = bufferedInputStream.read(dataBuffer, 0, 1024)) != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);
                        }
                    }
                    line="![]("+"./img/"+lastSegment+")";
                }
                if (startWriting) {
                    result.append(line).append("\n");
                }

            }
            mdStr2file(result.toString(),Paths.get(mdFile.toString()),p);
        } else {
            addLog("请求失败，status: ["+responseCode+"] url： "+urlStr,"D:\\md\\request_error.log");
        }
    }

    public static void addLog(String log,String logPath){

        try (FileWriter fileWriter = new FileWriter(logPath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write("["+new Date()+"]-"+ log);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHTML2md(String htmlStr) {
        return FlexmarkHtmlConverter.builder().build().convert(htmlStr);
    }

    public static void mdStr2file(String mdStr, Path path,int p) {
        // 定义文件路径
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(mdStr);
        } catch (IOException e) {
            addLog("写入文件时出错，[p="+p+"],e.msg："+e.getMessage(),"D:\\md\\file_write_error.log");
        }
    }

    private static String getTitle(String htmlContent){
        // 定义正则表达式来匹配 <title> 标签内部内容
        String regex = "<title>(.*?)</title>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlContent);
        String titleContent;
        // 查找匹配的内容
        if (matcher.find()) {
            titleContent = matcher.group(1);  // 获取匹配到的内容
        } else {
            titleContent= UUID.randomUUID().toString();
            addLog("未找到title标签，uuid: "+titleContent,"D:\\md\\title_create_error.log");
        }
        return titleContent;
    }

}
