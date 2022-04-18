package com.zcwxdqy.agedapp.util;

public class Strings {
    private static final int DELTA = 'a' - 'A';

    // 判断字符串是否为空串
    public static boolean isEmpty(String source) {
        return source == null || source.equals("");
    }

    /**
     * 首字母变小写
     * @return TestCase -> testCase
     */
    public static String firstLetterLowercase(String source) {
        if (isEmpty(source)) return source;
        StringBuilder res = processFirstLetterLowercase(source);
        int len = source.length();
        for (int i = 1; i < len; i++) {
            res.append(source.charAt(i));
        }
        return res.toString();
    }

    private static StringBuilder processFirstLetterLowercase(String source) {
        StringBuilder res = new StringBuilder();
        // 拼接首字符
        char firstChar = source.charAt(0);
        if (isBigLetter(firstChar)) {
            res.append((char) (firstChar + DELTA));
        } else {
            res.append(firstChar);
        }
        return res;
    }

    /**
     * 驼峰 -> 下划线
     * @return TestCase -> test_case
     */
    public static String camel2underline(String source) {
        if (isEmpty(source)) return source;
        StringBuilder res = processFirstLetterLowercase(source);
        // 其他字符
        int len = source.length();
        for (int i = 1; i < len; i++) {
            char c = source.charAt(i);
            if (isBigLetter(c)) {
                res.append("_");
                res.append((char) (c + DELTA));
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    /**
     * 下划线 -> 小驼峰
     * @return test_case -> testCase
     */
    public static String underline2smallCamel(String source) {
        return underline2camel(source, false);
    }

    /**
     * 下划线 -> 大驼峰
     * @return test_case -> TestCase
     */
    public static String underline2bigCamel(String source) {
        return underline2camel(source, true);
    }

    private static String underline2camel(String source, boolean big) {
        if (isEmpty(source)) return source;
        StringBuilder res = new StringBuilder();
        // 其他字符
        int len = source.length();
        // 上一个字符是下划线
        boolean prevUnderline = false;
        for (int i = 0; i < len; i++) {
            char c = source.charAt(i);
            if (c == '_') {
                prevUnderline = true;
                continue;
            }
            if (res.length() == 0) { // 首字符
                if (big && isSmallLetter(c)) { // 大驼峰
                    res.append((char) (c - DELTA));
                } else if (!big && isBigLetter(c)) { // 小驼峰
                    res.append((char) (c + DELTA));
                } else {
                    res.append(c);
                }
            } else if (prevUnderline && isSmallLetter(c)) {
                res.append((char) (c - DELTA));
            } else {
                res.append(c);
            }
            prevUnderline = false;
        }
        return res.toString();
    }

    public static boolean isBigLetter(char source) {
        return source >= 'A' && source <= 'Z';
    }

    public static boolean isSmallLetter(char source) {
        return source >= 'a' && source <= 'z';
    }

    /**
     * 返回第一个不为empty的字符串
     */
    public static String notEmpty(String... sources) {
        if (sources == null) return null;
        for (String source : sources) {
            if (!isEmpty(source)) return source;
        }
        return null;
    }
}
