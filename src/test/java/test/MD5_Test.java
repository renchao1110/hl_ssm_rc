package test;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5_Test {

    public static void main(String[] args) throws ParseException, IOException {
        //String md5Hex = DigestUtils.md5Hex("109765pjs123456");
        //System.out.println(md5Hex.toUpperCase());
        /*SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = s.parse("20181018105000");
        System.out.println(parse.getTime());*/
        //System.out.println(Math.ceil(7/2));
        File f = new File("C:\\Users\\admin\\Desktop\\a\\b\\a.txt");
        if (!f.exists()){
            System.out.println(f.exists());
            f.getParentFile().mkdirs();
            boolean newFile = f.createNewFile();
        }

        FileUtils.copyFileToDirectory(f,new File("C:\\Users\\admin\\Desktop\\c\\d"));
        //System.out.println(newFile);
    }
}
