package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.RecordModel;
import com.wsq.AssistantQ.service.AssistantService;
import com.wsq.AssistantQ.service.CourseService;
import com.wsq.AssistantQ.service.RecordService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:25
 */
@RestController
@CrossOrigin
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    //管理员新增工作记录 返回新增后的工作记录列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody RecordModel recordModel) {

        recordService.add(recordModel);

        //返回所有的工作记录列表
        return Result.success(findAllRecord().getData());
    }

    //管理员根据工作记录编号删除工作记录 返回删除后的工作记录列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody RecordModel recordModel) {
        recordService.deleteByObjectId(recordModel);

        //返回所有的工作记录列表
        return Result.success(findAllRecord().getData());
    }

    //管理员根据工作记录编号动态更改工作记录信息 返回更改后的工作记录列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody RecordModel recordModel) {
        recordService.modify(recordModel);

        //返回所有的工作记录列表
        return Result.success(findAllRecord().getData());
    }

    //管理员查找所有工作记录 返回工作记录列表
    @GetMapping(value = "/admin/findAllRecord")
    public Result findAllRecord() {
        List<RecordModel> list = recordService.findAllRecord();
        return Result.success(list);
    }

    //管理员根据提交者姓名查找工作记录 返回工作记录列表
    @GetMapping(value = "/admin/findByRecoSubmitterName")
    public Result findByRecoSubmitterName(@RequestBody RecordModel recordModel) {
        List<RecordModel> list = recordService.findByRecoSubmitterName(recordModel.getRecoSubmitterName());

        return Result.success(list);
    }

    //管理员根据工作记录审核状态查找工作记录 返回工作记录列表
    @GetMapping(value = "/admin/findByRecoStatus")
    public Result findByRecoStatus(@RequestBody RecordModel recordModel) {
        List<RecordModel> list = recordService.findByRecoStatus(recordModel.getRecoStatus());

        return Result.success(list);
    }

    //学生新增工作记录 返回新增后的自己的工作记录列表
    @PostMapping(value = "/student/addMy")
    public Result addMy(@RequestBody RecordModel recordModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String recoSubmitterId=userService.findByObjectId(objectId).getUserId();

        //添加提交者ID
        recordModel.setRecoSubmitterId(recoSubmitterId);

        //获取审核者ID 等同于课程信息表中根据课程名称（唯一的）获取提交者ID
        String recoAuditorId=courseService.findByCourName(recordModel.getRecoCourse()).getCourSubmitterId();

        //添加审核者ID
        recordModel.setRecoAuditorId(recoAuditorId);

        recordService.add(recordModel);

        //返回自己的工作记录列表
        return Result.success(findMy().getData());
    }

    //学生根据工作记录编号删除工作记录 返回删除后的自己的工作记录列表
    @PostMapping(value = "/student/deleteMyByObjectId")
    public Result deleteMyByObjectId(@RequestBody RecordModel recordModel) {
        recordService.deleteByObjectId(recordModel);

        //返回自己的工作记录列表
        return Result.success(findMy().getData());
    }

    //学生根据工作记录编号动态更改工作记录信息 返回更改后的自己的工作记录列表
    @PostMapping(value = "/student/modifyMy")
    public Result modifyMy(@RequestBody RecordModel recordModel) {
        recordService.modify(recordModel);

        //返回自己的工作记录列表
        return Result.success(findMy().getData());
    }

    //学生查看自己工作记录信息 返回自己的工作记录列表
    @GetMapping(value = "/student/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String recoSubmitterId=userService.findByObjectId(objectId).getUserId();

        List<RecordModel> list = recordService.findByRecoSubmitterId(recoSubmitterId);
        return Result.success(list);
    }

    //教师根据工作记录编号动态更改自己需要审核的工作记录信息 返回更改后的自己审核的工作记录列表
    @PostMapping(value = "/teacher/modifyMyAudit")
    public Result modifyMyAudit(@RequestBody RecordModel recordModel) {
        recordService.modify(recordModel);

        //返回自己审核的工作记录列表
        return Result.success(findMyAudit().getData());
    }

    //教师查看自己需要审核的工作记录信息 返回自己审核的工作记录列表
    @GetMapping(value = "/teacher/findMyAudit")
    public Result findMyAudit() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取审核者ID 等同于用户信息表中获取用户ID
        String recoAuditorId=userService.findByObjectId(objectId).getUserId();

        List<RecordModel> list = recordService.findByRecoAuditorId(recoAuditorId);
        return Result.success(list);
    }
}
