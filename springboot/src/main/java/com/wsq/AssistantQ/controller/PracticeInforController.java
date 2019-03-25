package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.PracticeInforModel;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.CurrentUserService;
import com.wsq.AssistantQ.service.PracticeInforService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CYann
 * @date 2018-04-01 19:15
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class PracticeInforController {
    @Autowired
    private PracticeInforService practiceInforService;
    @Autowired
    private CurrentUserService currentUserService;


    //新增实习生信息
    @PostMapping(value = "/addpractice")
    public Result addUserPracticeInfor(@RequestBody PracticeInforModel practiceInforModel){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        practiceInforModel.setStuNumber(item.getStuNumber());
        practiceInforService.add(practiceInforModel);
        return Result.success();
    }

    //展示实习生信息
    @GetMapping(value = "/listpractice")
    public Result listUserPracticeInfor(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        List<PracticeInforModel> list = practiceInforService.findByStuNum(item.getStuNumber());
        List<Map> mapList = new ArrayList();
        for(int i=0;i<list.size();i++){
            Map tempMap = new HashMap();
            PracticeInforModel practiceInforModel = list.get(i);
            tempMap.put("company",practiceInforModel.getCompany());
            tempMap.put("companyAddress",practiceInforModel.getCompanyAddress());
            tempMap.put("industry",practiceInforModel.getIndustry());
            tempMap.put("occupation",practiceInforModel.getOccupation());
            tempMap.put("salary",practiceInforModel.getSalary());
            tempMap.put("startDate",practiceInforModel.getStartDate());
            tempMap.put("endDate",practiceInforModel.getEndDate());
            // tempMap.put("createTime",practiceInforModel.getCreatTime());
            mapList.add(tempMap);
        }
        return Result.success(mapList);
    }

    //展示实习生信息
    @PostMapping(value = "/showuserpractice")
    public Result showUserPracticeInfor(@RequestBody UserModel userModel){
        List<PracticeInforModel> list = practiceInforService.findByStuNum(userModel.getStuNumber());
        List<Map> mapList = new ArrayList();
        for(int i=0;i<list.size();i++){
            Map tempMap = new HashMap();
            PracticeInforModel practiceInforModel = list.get(i);
            tempMap.put("company",practiceInforModel.getCompany());
            tempMap.put("companyAddress",practiceInforModel.getCompanyAddress());
            tempMap.put("industry",practiceInforModel.getIndustry());
            tempMap.put("occupation",practiceInforModel.getOccupation());
            tempMap.put("salary",practiceInforModel.getSalary());
            tempMap.put("startDate",practiceInforModel.getStartDate());
            tempMap.put("endDate",practiceInforModel.getEndDate());
            // tempMap.put("createTime",practiceInforModel.getCreatTime());
            mapList.add(tempMap);
        }
        return Result.success(mapList);
    }

}
