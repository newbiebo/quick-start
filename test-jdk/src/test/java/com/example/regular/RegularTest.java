package com.example.regular;

import org.junit.Test;

public class RegularTest {

    /**
     * 正侧文件名不能包含下列任何字符  \ /  : * ? " < > |
     */
    @Test
    public void aaa(){
        String INVALID_CHAR_PATTERN = "[\\\\/:*?\"<>|]";
        // 测试用例
        String[] testFilenames = {
                "validFile.txt",
                "invalid:file.txt",
                "another\\invalid|file.txt",
                "question?mark.txt"
        };
        for (String filename : testFilenames) {
            // 验证文件名是否合法的方法
            if (!filename.matches(".*" + INVALID_CHAR_PATTERN + ".*")) {
                System.out.println(filename + " 是合法的文件名。");
            } else {
                System.out.println(filename + " 包含非法字符。");
            }
        }
    }
}
