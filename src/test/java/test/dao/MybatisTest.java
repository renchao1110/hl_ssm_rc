package test.dao;

import com.hl95.ssm.dao.*;
import com.hl95.ssm.entity.Address;
import com.hl95.ssm.entity.MsgTemplet;
import com.hl95.ssm.entity.SendTplsms;
import com.hl95.ssm.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能：单元测试
 *
 * @author rc
 */


public class MybatisTest {


    private SqlSessionFactory sessionFactory;
    private Map<String,Object> params = new HashMap<>(16);
    @Before
    public void init(){
        //1.创建SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = null;
        try {
            //2.加载SqlMapConfig.xml配置文件D:\idea_workspace\hl_ssm_rc\src\test\resources\SqlMapConfig.xml
            inputStream = Resources.getResourceAsStream("config/SqlMapConfig.xml");
            //加载log4j配置
            //PropertyConfigurator.configure(Resources.getResourceAsProperties("loj4j.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件异常==="+e.getMessage());
        }
        //3.创建SqlSessionFactory
        sessionFactory = sqlSessionFactoryBuilder.build(inputStream);
    }

    /**
     * 使用动态代理得DAO开发
     *
     */

    /*@Test
    public void testMapperDao4(){

        SqlSession session = sessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        params.put("tplContent","撒大苏打");
        params.put("status","00");
        params.put("reason","ok:成功");
        //int i = mapper.saveMsgTemplet(params);
        User user = mapper.findUserBySnid("109765");
        session.commit();
        session.close();
        System.out.println(user);


    }
    @Test
    public void testMapperDao(){

        SqlSession session = sessionFactory.openSession();
        MsgTempletMapper mapper = session.getMapper(MsgTempletMapper.class);
        params.put("tplContent","撒大苏打");
        params.put("status","00");
        params.put("reason","ok:成功");
        mapper.saveMsgTemplet(params);
        session.commit();
        session.close();
        System.out.println(params.get("id"));
        //System.out.println(templet);


    }


    @Test
    public void testMapperDao2(){

        SqlSession session = sessionFactory.openSession();
        MsgTempletMapper mapper = session.getMapper(MsgTempletMapper.class);
        params.put("tpl_content","撒大苏打");
        params.put("status","00");
        params.put("reason","ok:成功");
        MsgTemplet m = new MsgTemplet();
        m.setTpl_id("1");
        m.setReason("测试");
        m.setStatus("0");
        m.setTpl_content("cs测试");
        int i = mapper.insert(m);
        System.out.println(i);


    }


    *//*@Test
    public void testMapperDao3(){

        SqlSession session = sessionFactory.openSession();
        MsgTempletMapper mapper = session.getMapper(MsgTempletMapper.class);
        MsgTemplet msgTemplet = mapper.selectByPrimaryKey("3fe55087bcad11e88ebe507b9da80438");
        String[] msgTemplets = msgTemplet.getTpl_content().split("\\{n\\}");
        for (String sms:msgTemplets){
            System.out.println(sms);
        }



    }*//*

    @Test
    public void testMapperDao5(){

        SqlSession session = sessionFactory.openSession();
        AddressMapper mapper = session.getMapper(AddressMapper.class);
        int countIp = mapper.getCountIp("127.0.0.1");
        System.out.println(countIp);


    }


    *//*@Test
    public void testMapperDao6(){

        SqlSession session = sessionFactory.openSession();
        SendTplsmsMapper mapper = session.getMapper(SendTplsmsMapper.class);
        List<Map<String, Object>> maps = new ArrayList<>();

        for (int i=0;i<10;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("tpl_id","111");
            map.put("tpl_content","测试内容");
            map.put("mobile","11111112222");
            map.put("stime","2018-10-19 12:30:00");
            map.put("ext","222");
            map.put("status","0");
            map.put("reason","成功");
            maps.add(map);
        }
        mapper.saveBatchMap(maps);



    }*//*

    @Test
    public void testMapperDao7(){

        SqlSession session = sessionFactory.openSession();
        SendTplsmsMapper mapper = session.getMapper(SendTplsmsMapper.class);
        List<SendTplsms> maps = new ArrayList<>();

        for (int i=0;i<10;i++){
            SendTplsms s = new SendTplsms();
            s.setRrid(UUID.randomUUID().toString().replace("-",""));
            //s.setExt("222");
            s.setMobile("111111333333");
            s.setReason("成功");
            //s.setState("00");
            s.setStatus("0");
            //s.setStime("33");
            s.setTpl_content("测试信息");
            s.setTpl_id("444dssd4");
            //String s1 = UUID.randomUUID().toString().replace("-", "");
            maps.add(s);
        }
        try {

            int i = mapper.saveBatch(maps);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(maps);
        session.commit();

    }



    @Test
    public void testMapperDao8(){

        SqlSession session = sessionFactory.openSession();
        SendTplSmsResultMapper mapper = session.getMapper(SendTplSmsResultMapper.class);
        List<String> l = new ArrayList<>();
        l.add("3b680712a747418ebb432e7a607c1d31");
        l.add("5ad1de5264054ab7a693caff4f7c138c");
        l.add("8814cc3a80f64cf3ba7fa6f6959014c4");
        l.add("b2907e248a4e4dec886eea800ed40954");
        l.add("bce7eb4f870645da9e54d5ee287b9a04");
        int i = mapper.saveBatch(l);
        session.commit();
        System.out.println(i);
    }*/

}
