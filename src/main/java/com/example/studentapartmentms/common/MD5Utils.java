package com.example.studentapartmentms.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具
 */
public class MD5Utils {

    /**
     * 计算 MD5 值
     * @param input 要计算的文本
     * @return MD5
     */
    public static String getMd5Hash(String input) {
        try {
            // 获取 MessageDigest 实例用于 MD5 算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将要加密的字符串转换为字节数组
            byte[] messageDigestData = input.getBytes(StandardCharsets.UTF_8);

            // 对字节数组进行 MD5 计算
            byte[] md5Bytes = md.digest(messageDigestData);

            // 将得到的字节数组转换为十六进制表示形式
            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                sb.append(String.format("%02x", b));
            }

            // 返回 MD5 哈希值
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("未能初始化MD5实例!", e);
        }
    }
}
