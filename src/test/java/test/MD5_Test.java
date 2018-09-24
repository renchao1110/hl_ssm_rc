package test;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5_Test {

    public static void main(String[] args) {
        String md5Hex = DigestUtils.md5Hex("109765pjs123456");
        System.out.println(md5Hex);
    }
}
