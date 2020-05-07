package com.utils;

/**
 * DES加密、解密算法CBC模式Base64输入输出
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncryptUtils {

    private static Logger logger = LoggerFactory.getLogger(DesEncryptUtils.class);

    private static final String key = "*:@1$8!g";

    private static DesEncryptUtils instance = null;

    public static DesEncryptUtils getInstance() {
        if (instance == null) {
            instance = new DesEncryptUtils();
        }
        return instance;
    }

    // 加密
    public static String encrypt(String sSrc) {
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0, 8).getBytes("UTF-8"), "DES");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            String encryptString = new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
            //将加密后的字符串格式化
            return doFormat(encryptString);
        } catch (Exception e) {
            logger.error("encrypt-exception:{}", e);
            return null;
        }

    }

    // 解密
    public static String decrypt(String sSrc) {
        try {
            //反格式化加密后的字符串
            sSrc = unFormat(sSrc);
            SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0, 8).getBytes("UTF-8"), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        } catch (Exception e) {
            logger.error("encrypt-decrypt:{}", e);
            return null;
        }
    }


    /**
     * 将base64种的敏感字符+,/,=转化为_,-,. 以及base64会在编码串中产生换行符，虽然解码会不管
     */
    public static String doFormat(String str) {
        if (str == null) {
            return "";
        }
        str = str.replaceAll("\\+", "_");
        str = str.replaceAll("/", "-");
        str = str.replaceAll("=", ".");
        str = str.replaceAll("\\s", "");
        return str;
    }

    public static String unFormat(String str) {
        if (str == null) {
            return "";
        }
        str = str.replaceAll("_", "+");
        str = str.replaceAll("-", "/");
        str = str.replaceAll("\\.", "=");
        return str;
    }


    public static void main(String[] args) {
        // 需要加密的字串
        String cSrc = "abc";
        System.out.println("加密前的字串是：" + cSrc);
        // 加密
        String enString = DesEncryptUtils.getInstance().encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);
        // 解密
        String DeString = DesEncryptUtils.getInstance().decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
    }
}