package com.hl95.ssm.service.impl;

import com.hl95.ssm.dao.SmsConductMapper;
import com.hl95.ssm.dao.SmsTaskMapper;
import com.hl95.ssm.dao.StateReportMapper;
import com.hl95.ssm.entity.SmsConduct;
import com.hl95.ssm.service.CEBStateReportService;
import com.hl95.ssm.util.read.ReadSNDFile;
import com.hl95.ssm.util.resolve.ParamsResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: hl_ssm_rc
 * @description: CEB获取状态报告的业务实现
 * @author: renchao
 **/
@Service
public class CEBStateReportServiceImpl implements CEBStateReportService {
    private static final String SUCCESS = "0";
    private static final String STATUS = "status";
    private static final String REASON = "成功";
    @Autowired
    private SmsConductMapper smsConductMapper;
    @Autowired
    private StateReportMapper stateReportMapper;
    @Autowired
    private SmsTaskMapper smsTaskMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 接收状态报告并根据状态报告FLinkID更新短信下发状态
     * @param request
     * @return
     */
    @Override
    public String saveReport(HttpServletRequest request) {
        //1.获取请求参数。
        Map<String, Object> params = ParamsResolve.getParams(request);
        //首次保存添加默认值
        params.put("state",SUCCESS);
        params.put("reason",REASON);
        //保存状态报告
        params.put("id","");
        int i = stateReportMapper.saveReport(params);
        //更新短信下发状态
        String smsid = (String) params.get("FLinkID");
        String state = "";
        if (i==1&&smsid!=null&&!"".equals(smsid)){
            Map<String,Object> map = new HashMap<>();
            map.put("id",smsid);
            map.put("requesttime",params.get("FSubmitTime"));
            state = (String)params.get("FReportCode");
            if ("DELIVRD".equals(state)||"0".equals(state)){
                map.put("reason","短信下发成功");
                map.put("state","0");
            }else {
                map.put("reason","短信下发失败");
                map.put("state","0");
            }
            smsConductMapper.updateByLinkId(map);
        }
        return state;
    }

    @Override
    public List<Map<String, Object>> getReportIds() {
        List<Map<String, String>> completeTasks = smsTaskMapper.getCompleteTask();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, String> task:completeTasks){
            List<Integer> linkId = new ArrayList<>();
            Map<String, Object> temp = new HashMap<>();
            List<Map<String, Object>> msgs = smsConductMapper.getMsgs(task.get("UserID"), task.get("SendID"));
            for (Map<String, Object> sms : msgs){
                linkId.add((Integer)sms.get("linkid"));
            }
            temp.put("fileName","["+task.get("UserID")+"]"+"_"+"["+task.get("SendID")+"].rpt");
            temp.put("reportIds",linkId);
            temp.put("taskId",task.get("id"));
            result.add(temp);
        }
        return result;
    }


    public void getReports(List<Map<String, Object>> list){
        List<String> taskIds = new ArrayList<>();
        List<Map<String,Object>> params = new ArrayList<>();
        for (Map<String, Object> temp:list){
            String taskId = "";
            if (temp.get("taskId") instanceof Integer){
                Integer ss = (Integer)temp.get("taskId");
                taskId = String.valueOf(ss);
            }
            taskIds.add(taskId);
            List<Integer> reportIds = (List<Integer>)temp.get("reportIds");
            String filename = (String) temp.get("fileName");
            List<Map<String, String>> reports = stateReportMapper.getReportsByids(reportIds);
            writeFile(filename,reports);
        }
        smsTaskMapper.updateCompleteStateById(taskIds);
    }


    public void writeFile(String fileName,List<Map<String, String>> list){
        Properties p = new Properties();
        InputStream in = CEBStateReportServiceImpl.class.getClassLoader().getResourceAsStream("config/fileURL.properties");
        PrintWriter pw = null;
        try {
            p.load(in);
            String rptURL = p.getProperty("rptURL");
            File rptFile = new File(rptURL+"\\"+fileName);
            if (!rptFile.exists()){
                rptFile.createNewFile();
            }
            pw = new PrintWriter(rptFile);
            pw.println(list.size());
            for (Map<String, String> content:list){
                String linkId = content.get("FLinkID");
                SmsConduct smsByLinkId = smsConductMapper.findSmsByLinkId(linkId);
                int number = 1;
                if (smsByLinkId.getSmcontent().length()>70){
                    Integer temp = smsByLinkId.getSmcontent().length()%67;
                    if(temp!=0){
                        number = temp + 1;
                    }else {
                        number = temp;
                    }
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append(content.get("FSubmitTime").trim())
                        .append("|*|")
                        .append(content.get("FDestAddr").trim())
                        .append("|*|")
                        .append(number)
                        .append("|*|")
                        .append(content.get("state").trim());
                        if("1".equals(content.get("state"))){
                            buffer.append("|*|")
                            .append(content.get("reason").trim());
                        }
                pw.println(buffer.toString());
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (pw!=null){
                pw.close();
            }
        }


    }
}
