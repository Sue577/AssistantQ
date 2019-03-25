package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.*;
import com.wsq.AssistantQ.service.AlumniInformationService;
import com.wsq.AssistantQ.service.CurrentUserService;
import com.wsq.AssistantQ.service.MsgService;
import com.wsq.AssistantQ.service.PracticeInforService;
import com.wsq.AssistantQ.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class CurrentUserController {
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private AlumniInformationService alumniInformationService;
    @Autowired
    private PracticeInforService practiceInforService;
    @Autowired
    private MsgService msgService;

    //增加用户
    @PostMapping(value = "/addcurrentuser")
    public Result addUser(@RequestBody CurrentUserModel currentUserModel){
        currentUserService.add(currentUserModel);
        return Result.success();
    }

    //当前用户
    @GetMapping(value = "/currentUser")
    public Result currentUser(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        List<MsgModel> list = msgService.findByRecUser(item.getLoginEmail());
        Map result = new HashMap();
        result.put("name", item.getStuName());
        result.put("avatar", item.getAvatar());
        result.put("notifyCount", list.size()); //消息功能推出时进行更正，目前写死
        result.put("userid", item.getStuNumber());
        return Result.success(result);
    }

    @GetMapping(value = "/home")
    public Result viewHome(){
        List<Object[]> list = alumniInformationService.findMaxAlumni();
        List<Object[]> list2 = practiceInforService.findMaxPractice();
        List<Map> returnList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] objects = list.get(i);
            Map tempMap = new HashMap();
            tempMap.put("company",objects[4]);
            tempMap.put("industry",objects[6]);
            tempMap.put("occupation",objects[7]);
            tempMap.put("salary",objects[8]);
            tempMap.put("stuNumber",objects[9]);
            tempMap.put("endDate",objects[10]);
            tempMap.put("startDate",objects[11]);

            tempMap.put("stuName",objects[13]);
            tempMap.put("stuMajor",objects[14]);
            tempMap.put("stuClass",objects[15]);

            tempMap.put("stuEndYear",objects[17]);
            tempMap.put("currentEmail",objects[18]);
            tempMap.put("currentPhone",objects[19]);
            tempMap.put("avatar",objects[20]);
            tempMap.put("cover",objects[20]);
            tempMap.put("tag","");
            returnList.add(tempMap);
        }
        for(int i=0;i<list2.size();i++){
            Object[] objects = list2.get(i);
            Map tempMap2 = new HashMap();
            tempMap2.put("company",objects[4]);
            tempMap2.put("industry",objects[6]);
            tempMap2.put("occupation",objects[7]);
            tempMap2.put("salary",objects[8]);
            tempMap2.put("stuNumber",objects[9]);
            tempMap2.put("endDate",objects[10]);
            tempMap2.put("startDate",objects[11]);

            tempMap2.put("stuName",objects[13]);
            tempMap2.put("stuMajor",objects[14]);
            tempMap2.put("stuClass",objects[15]);

            tempMap2.put("stuEndYear",objects[17]);
            tempMap2.put("currentEmail",objects[18]);
            tempMap2.put("currentPhone",objects[19]);
            tempMap2.put("avatar",objects[20]);
            tempMap2.put("cover",objects[20]);
            tempMap2.put("tag","Intern");
            returnList.add(tempMap2);
        }
        return Result.success(returnList);
    }
    @PostMapping(value = "/home/search")
    public Result searchHome(@RequestBody UserModel userModel){
        if(userModel == null ){
            List<Object[]> list = alumniInformationService.findMaxAlumni();
            List<Object[]> list2 = practiceInforService.findMaxPractice();
            List<Map> returnList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                Object[] objects = list.get(i);
                Map tempMap = new HashMap();
                tempMap.put("company",objects[4]);
                tempMap.put("industry",objects[6]);
                tempMap.put("occupation",objects[7]);
                tempMap.put("salary",objects[8]);
                tempMap.put("stuNumber",objects[9]);
                tempMap.put("endDate",objects[10]);
                tempMap.put("startDate",objects[11]);

                tempMap.put("stuName",objects[13]);
                tempMap.put("stuMajor",objects[14]);
                tempMap.put("stuClass",objects[15]);

                tempMap.put("stuEndYear",objects[17]);
                tempMap.put("currentEmail",objects[18]);
                tempMap.put("currentPhone",objects[19]);
                tempMap.put("avatar",objects[20]);
                tempMap.put("cover",objects[20]);
                tempMap.put("tag","");
                returnList.add(tempMap);
            }
            for(int i=0;i<list2.size();i++){
                Object[] objects = list2.get(i);
                Map tempMap2 = new HashMap();
                tempMap2.put("company",objects[4]);
                tempMap2.put("industry",objects[6]);
                tempMap2.put("occupation",objects[7]);
                tempMap2.put("salary",objects[8]);
                tempMap2.put("stuNumber",objects[9]);
                tempMap2.put("endDate",objects[10]);
                tempMap2.put("startDate",objects[11]);

                tempMap2.put("stuName",objects[13]);
                tempMap2.put("stuMajor",objects[14]);
                tempMap2.put("stuClass",objects[15]);

                tempMap2.put("stuEndYear",objects[17]);
                tempMap2.put("currentEmail",objects[18]);
                tempMap2.put("currentPhone",objects[19]);
                tempMap2.put("avatar",objects[20]);
                tempMap2.put("cover",objects[20]);
                tempMap2.put("tag","Practice");
                returnList.add(tempMap2);
            }
            return Result.success(returnList);
        }
        else {
            List<Object[]> list = alumniInformationService.searchMaxAlumni(userModel.getStuName(),userModel.getStuClass(),userModel.getStuMajor(),userModel.getStuNumber());
            List<Object[]> list2 = practiceInforService.searchMaxPractice(userModel.getStuName(),userModel.getStuClass(),userModel.getStuMajor(),userModel.getStuNumber());
            List<Map> returnList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                Object[] objects = list.get(i);
                Map tempMap = new HashMap();
                tempMap.put("company",objects[4]);
                tempMap.put("industry",objects[6]);
                tempMap.put("occupation",objects[7]);
                tempMap.put("salary",objects[8]);
                tempMap.put("stuNumber",objects[9]);
                tempMap.put("endDate",objects[10]);
                tempMap.put("startDate",objects[11]);

                tempMap.put("stuName",objects[13]);
                tempMap.put("stuMajor",objects[14]);
                tempMap.put("stuClass",objects[15]);

                tempMap.put("stuEndYear",objects[17]);
                tempMap.put("currentEmail",objects[18]);
                tempMap.put("currentPhone",objects[19]);
                tempMap.put("avatar",objects[20]);
                tempMap.put("cover",objects[20]);
                tempMap.put("tag","");
                returnList.add(tempMap);
            }
            for(int i=0;i<list2.size();i++){
                Object[] objects = list2.get(i);
                Map tempMap2 = new HashMap();
                tempMap2.put("company",objects[4]);
                tempMap2.put("industry",objects[6]);
                tempMap2.put("occupation",objects[7]);
                tempMap2.put("salary",objects[8]);
                tempMap2.put("stuNumber",objects[9]);
                tempMap2.put("endDate",objects[10]);
                tempMap2.put("startDate",objects[11]);

                tempMap2.put("stuName",objects[13]);
                tempMap2.put("stuMajor",objects[14]);
                tempMap2.put("stuClass",objects[15]);

                tempMap2.put("stuEndYear",objects[17]);
                tempMap2.put("currentEmail",objects[18]);
                tempMap2.put("currentPhone",objects[19]);
                tempMap2.put("avatar",objects[20]);
                tempMap2.put("cover",objects[20]);
                tempMap2.put("tag","Practice");
                returnList.add(tempMap2);
            }
            return Result.success(returnList);
        }

    }

    //修改密码
    @PostMapping(value = "/modifypwd")
    public Result modifyUserPwd(@RequestBody CurrentUserModel currentUserModel){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        // System.out.println(password);
        currentUserService.updateMyPwd(currentUserModel,currentUserModel.getPassword(),objectId);
        return Result.success();
    }

}
