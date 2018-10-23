package com.hl95.ssm.util.read;

import com.hl95.ssm.dao.FilesMapper;
import com.hl95.ssm.util.resolve.ResolveXml;
import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @program: hl_ssm_rc
 * @description: 用来解析snd格式文件
 * @author: renchao
 * @create: 2018-10-11 15:59
 **/
@Component
public class ReadSNDFile {
    @Autowired
    private FilesMapper filesMapper;
    private static final Pattern PATTERN = Pattern.compile("(<FileTask>(.*)</FileTask>)");//[a-zA-Z0-9.]
    private static final Pattern PATTERNXML = Pattern.compile("^<[a-zA-Z]+(>)$|^<[a-zA-Z]+(>){1}[\\s\\S]*(</){1}[a-zA-Z]+(>)$|(^(</)[a-zA-Z]+(>)$)");
    private static final Pattern PATTERNMSG = Pattern.compile("^[\\d]+");
    private static  File DESTFILE = null;//new File("D:\\dest_snd");
    private static  File ERRORFILE = null;//new File("D:\\error_snd");
    private static String fileURL = null;

    static {
        Properties p = new Properties();
        InputStream in = ReadSNDFile.class.getClassLoader().getResourceAsStream("config/fileURL.properties");
        try {
            p.load(in);
            String destURL = p.getProperty("destURL");
            String errorURL = p.getProperty("errorURL");
            fileURL = p.getProperty("sndURL");
            DESTFILE = new File(destURL);
            ERRORFILE = new File(errorURL);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public List<Map<String,String>> readSNDFile() throws IOException {
        List<Map<String,String>> list = new ArrayList<>();
        InputStream in = null;
        BufferedReader br = null;
        String path = getFilepaths(fileURL);
        if ("".equals(path)||path==null){
            return list;
        }
        File file = new File(path);
        if (filesMapper.count(file.getName())>0){
            String error = "ERROR---"+new Date().getTime()+"---"+file.getName();
            File errorFile = new File(file.getParent()+"\\"+error);
            boolean b = file.renameTo(errorFile);
            FileUtils.copyFileToDirectory(errorFile,ERRORFILE);
            FileUtils.deleteQuietly(errorFile);
            return list;
        }
        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("filename",file.getName());
        fileMap.put("state","1");
        filesMapper.save(fileMap);
        try {
            Map<String,String> sndMap = new HashMap<>();
            StringBuffer sbXML = new StringBuffer();
            StringBuffer sbMsg = new StringBuffer();
            String temp = "";
            FileUtils.copyFileToDirectory(file,DESTFILE);
            in = new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(in,"GBK"));
            while ((temp=br.readLine())!=null){
                if (PATTERNXML.matcher(temp.trim()).find()){
                    sbXML.append(temp.trim());
                }
                if (PATTERNMSG.matcher(temp.trim()).find()){
                    sbMsg.append(temp.trim()).append("|$|");

                }
            }
            sndMap.put("xml",sbXML.toString());
            sndMap.put("msg",sbMsg.toString());
            list.add(sndMap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    public static String getFilepaths(String path) {
        String filePatch = "";
        List<String> list = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return filePatch;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        return getFilepaths(file2.getAbsolutePath());
                    } else if (file2.isFile()&&file2.getAbsolutePath().endsWith(".snd")){
                        String temppath = file2.getAbsolutePath();
                        list.add(temppath);
                        return temppath;
                    }
                }
            }
        } else {
            return filePatch;
        }
        return filePatch;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        //System.out.println(getFilepaths("D:\snd"));
        //System.out.println(readSNDFile("D:\\snd"));
        List<Map<String, String>> mapList = new ReadSNDFile().readSNDFile();
        List<Map> saveParams = ResolveXml.getSaveParams(mapList);
        for (Map map:saveParams){
                Object o1 = map.get("paremXml");
                Object o2 = map.get("paremMsg");
                System.out.println(o1);
                System.out.println(o1.getClass().getTypeName());
                System.out.println(o2);
                System.out.println(o2.getClass().getTypeName());

        }
        System.out.println(saveParams.size());
    }
}
