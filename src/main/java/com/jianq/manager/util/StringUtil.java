package com.jianq.manager.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 字符串帮助工具类
 *
 * @author zhoudl
 */
public class StringUtil {

    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    /**
     * 产生随机数，并把该随机数返回出来
     *
     * @return 随机数
     */
    public static String getRadomString() {
        Random radom = new Random();
        return String.valueOf(radom.nextInt());
    }

    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }

    public static String upperCharacter(Integer order) {
        if (order == null)
            return "";
        char s = (char) (order.intValue() + 64);
        return String.valueOf(s);
    }

    /**
     * 杀空函数，将"null"和null对象转换为""
     *
     * @param str String 待处理的字符串
     * @return String 处理后的字符串
     */
    public static String killNull(String str) {
        String returnStr = null;
        if (str == null || "null".equals(str.toLowerCase())) {
            returnStr = "";
        } else {
            returnStr = str;
        }
        return returnStr;
    }

    /**
     * 判断某字符串是否为空，为空的标准是 str==null 或 str.length()==0
     *
     * @param str String 待处理的字符串
     * @return boolean 字符串为空返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断某字符串是否非空，等于 !isEmpty(String str)
     *
     * @param str String 待处理的字符串
     * @return boolean 字符串为非空返回true，否则返回false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断某字符串是否为空或长度为0或由空白符构成
     *
     * @param str String 待处理的字符串
     * @return boolean 字符串为为空或长度为0或由空白符构成返回true，否则返回false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断某字符串是否不为空且长度不为0且不由空白符构成，等于 !isBlank(String str)
     *
     * @param str String 待处理的字符串
     * @return boolean 字符串不为空且长度不为0且不由空白符构成返回true，否则返回false
     */
    public static boolean isNotBlank(String str) {
        return !StringUtil.isBlank(str);
    }

    /**
     * 去除字符串两边的空格并处理空字符串
     *
     * @param str String
     * @return String
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 将字符串 source 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找
     *
     * @param source String
     * @param oldStr String
     * @param newStr String
     * @return String
     */
    public static String replace(String source, String oldStr, String newStr) {
        return replace(source, oldStr, newStr, true);
    }

    /*
     * /** 将字符串 source 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找
     *
     * @param source String
     *
     * @param oldStr String
     *
     * @param newStr String
     *
     * @param matchCase boolean
     *
     * @return String
     */
    public static String replace(String source, String oldStr, String newStr,
                                 boolean matchCase) {
        if (isEmpty(source)) {
            return null;
        }
        // 首先检查旧字符串是否存在, 不存在就不进行替换
        if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
            return source;
        }
        int findStartPos = 0;
        int a = 0;
        while (a > -1) {
            int b = 0;
            String str1, str2, str3, str4, strA, strB;
            str1 = source;
            str2 = str1.toLowerCase();
            str3 = oldStr;
            str4 = str3.toLowerCase();
            if (matchCase) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }
            a = strA.indexOf(strB, findStartPos);
            if (a > -1) {
                b = oldStr.length();
                findStartPos = a + b;
                StringBuffer bbuf = new StringBuffer(source);
                source = (bbuf.replace(a, a + b, newStr)).toString();
                // 新的查找开始点位于替换后的字符串的结尾
                findStartPos = findStartPos + newStr.length() - b;
            }
        }
        return source;
    }

    /**
     * 根据以指定分隔符分隔的字符串参数转换为去除分隔符的数组
     *
     * @param string 例如"1,2,3,4,5,6"
     * @return String[]
     */

    public static String[] stringToArray(String string, String splitWord) {
        String[] tmpArray = null;
        if (StringUtil.isNotBlank(string)) {
            if (StringUtil.isNotBlank(splitWord)) {
                tmpArray = string.split(splitWord);
            } else {
                tmpArray = new String[1];
                tmpArray[0] = string;
            }
        }
        return tmpArray;
    }

    /**
     * 在字符串中查找是否存在指定字符串
     *
     * @return boolean
     */
    private static boolean checkOther(String dist, String other) {
        for (int i = 0; i < other.length(); i++) {
            if (dist.indexOf(other.substring(i, i + 1)) != -1)
                return false;
        }
        return true;
    }

    /**
     * 滤除内容中的危险 HTML 代码, 主要是脚本代码, 滚动字幕代码以及脚本事件处理代码
     *
     * @param content String
     * @return String
     */
    public static String replaceHtmlCode(String content) {
        if (isEmpty(content))
            return "";
        // 需要滤除的脚本事件关键字
        String[] eventKeywords = {"onmouseover", "onmouseout", "onmousedown",
                "onmouseup", "onmousemove", "onclick", "ondblclick",
                "onkeypress", "onkeydown", "onkeyup", "ondragstart",
                "onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
                "onrowexit", "onselectstart", "onload", "onunload",
                "onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
                "onscroll", "oncontextmenu"};
        content = replace(content, "<script", "&ltscript", false);
        content = replace(content, "</script", "&lt/script", false);
        content = replace(content, "<marquee", "&ltmarquee", false);
        content = replace(content, "</marquee", "&lt/marquee", false);
        content = replace(content, "\r\n", "<BR>");
        // 滤除脚本事件代码
        for (int i = 0; i < eventKeywords.length; i++) {
            content = replace(content, eventKeywords[i],
                    "_" + eventKeywords[i], false); // 添加一个"_", 使事件代码无效
        }
        return content;
    }

    public static String utf2gbk(String str) {
        if (str != null && !"".equals(str.trim())) {
            try {
                return URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String gbk2utf(String str) {
        if (str != null && !"".equals(str.trim())) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换编码 ISO-8859-1到GB2312
     *
     * @param text String
     */
    public static String ISO2GB(String text) {
        String result = "";
        try {
            if (StringUtil.isNotEmpty(text)) {
                result = new String(text.getBytes("ISO-8859-1"), "GB2312");
            }
        } catch (UnsupportedEncodingException ex) {
            result = ex.toString();
        }
        return result;

    }

    /**
     * 转换编码 GB2312到ISO-8859-1
     *
     * @param text String
     */
    public static String GB2ISO(String text) {
        String result = "";
        try {
            if (StringUtil.isNotEmpty(text)) {
                result = new String(text.getBytes("GB2312"), "ISO-8859-1");
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    /**
     * 转换编码 GB2312到ISO-8859-1
     *
     * @param text String
     */
    public static String iso2Utf8(String text) {
        String result = "";
        try {
            if (StringUtil.isNotEmpty(text)) {
                result = new String(text.getBytes("iso-8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    /**
     * 字符串编码不同编码之间的转换,将字符串从编码coding0转换为coding1
     *
     * @param text    String
     * @param coding0 字符编码0
     * @param coding1 字符编码1
     */
    public static String transcoding(String text, String coding0, String coding1) {
        String result = "";
        if (text != null && text.length() > 0) {
            try {
                result = new String(text.getBytes(coding0), coding1);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }

    /**
     * 把Str加密返回加密码，同时把“%”转换为$
     *
     * @param str    String 源字符串
     * @param coding String 编码方式:"UTF-8"
     * @return 被转换后的字符串
     */
    public static String uRLEncoder(String str, String coding)
            throws UnsupportedEncodingException {
        String ENStr = "";
        if (str != null) {
            ENStr = java.net.URLEncoder.encode(str, coding);
            ENStr = ENStr.replace('%', '$');
        }
        return ENStr;
    }

    /**
     * 16进制数组字符
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 字符串翻转
     *
     * @param str String
     * @return String
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 判断对象是否为空，为空的标准是 str==null 或 str.length()==0
     *
     * @param o Object 待处理的对象
     * @return boolean 对象为空返回true，否则返回false
     */

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        String s = null;
        if (!(o instanceof String)) {
            s = o.toString();
        } else {
            s = (String) o;
        }

        if (s == null || s.trim().length() == 0 || "null".equals(s)) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象是否为空，为空的标准是 str==null 或 str.length()==0
     *
     * @param o Object 待处理的对象
     * @return boolean 对象为空返回true，否则返回false
     */

    public static boolean isEmptyAll(Object... os) {
        if (os == null || os.length < 1) {
            return true;
        }
        boolean flag = true;
        for (int i = 0; i < os.length; i++) {
            flag = isEmpty(os[i]);
            if (!flag) {
                return false;
            }
        }
        return flag;
    }

    /**
     * 判断对象是否非空
     *
     * @param o Object 待处理的对象
     * @return boolean 对象为非空返回true，否则返回false
     */

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 返回数组元素中某个值的下标
     *
     * @param array String[] 数组
     * @param value String 某个值
     * @return int 对象为空或不存在返回0，否则返回下标值
     */
    public static int getArrayIndex(String[] array, String value) {
        if (array != null) {
            if (array.length > 0) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(value)) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 从字符串中查找字符串。 规则如下示例。 $2002$$2003$","$",",") 返回：2002$,2003
     * *2002**2003**5465464**2000**2004**20*","*",",")
     * 返回：2002,2003,5465464,2000,2004,20
     *
     * @param src   String 源字符串
     * @param regex String 拆分字符串
     * @param other String 拆分字符串
     * @return String static
     */
    public static String replaceWith(String source, String oldStr, String newStr) {
        source = StringUtil.replace(source, oldStr + oldStr, newStr);
        source = StringUtil.replace(source, oldStr, "");
        source = StringUtil.trim(source);
        return source;
    }

    /**
     * 获得请求的真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (!ips[i].equals("")
                        && !ips[i].toLowerCase().equals("unknown")) {
                    ip = ips[i];
                }
            }
        }
        return ip;
    }

    // 将POJO转换成JSON
    public static String bean2json(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按照指定长度截取字符串
     *
     * @param str
     * @param index
     * @return
     */
    public static String substring(String str, int index) {
        if (isEmpty(str)) {
            return "";
        } else if (str.length() <= index) {
            return str;
        } else {
            return str.substring(str.length() - index, str.length());
        }
    }

    /**
     * 追加指定字符串到指定的长度
     *
     * @param srcStr            源字符串
     * @param needLength        需要达到的长度
     * @param code              需要追加的字符,只能是长度为1的。eg:"0"
     * @param appendStartNotEnd true：追加在前面 false:追加在后面
     * @return
     */
    public static String appendStrToLength(String srcStr, Integer needLength,
                                           String code, boolean appendStartNotEnd) {
        if (StringUtil.isBlank(srcStr)) {
            srcStr = "";
        }
        // 原始字符串长度
        int srcLength = srcStr.length();
        if (srcLength < needLength) {
            for (int i = 0; i < needLength - srcLength; i++) {
                if (appendStartNotEnd) {
                    // 加到前面
                    srcStr = code + srcStr;
                } else {
                    // 加到后面
                    srcStr = srcStr + code;
                }
            }
        }
        return srcStr;
    }

    /**
     * 字符串 中文转码
     *
     * @param keyword
     * @param oldEncodingName 原始编码 eg:iso-8859-1
     * @param newEncodingName 目标编码 eg:utf-8
     * @return
     */
    public static String parseStrEncoding(String keyword,
                                          String oldEncodingName, String newEncodingName) {

        log.info("转码之前的字符串是：" + keyword);
        if (StringUtil.isBlank(keyword)) {
            return "";
        }
        try {
            keyword = new String(keyword.getBytes(oldEncodingName),
                    newEncodingName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("转码之后的字符串是：" + keyword);
        return keyword;
    }

    public static void main(String[] args) {
        System.out.println(isEmptyAll(null, null, null));
        System.out.println(isEmptyAll("", "", ""));
        System.out.println(isEmptyAll(null, "", "123"));
        System.out.println(isEmptyAll("123", "", "123"));
        System.out.println(isEmptyAll("abc", "hjjii", "123"));
    }

    /**
     * 是否包含null或者空
     *
     * @param os
     * @return
     */
    public static boolean containNullOrEmpty(Object... os) {
        if (os == null || os.length < 1) {
            return true;
        }
        boolean flag = false;
        for (int i = 0; i < os.length; i++) {
            flag = isEmpty(os[i]);
            if (flag) {
                return true;
            }
        }
        return flag;
    }
}
