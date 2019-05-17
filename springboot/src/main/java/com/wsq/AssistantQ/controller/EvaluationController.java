package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.EvaluationModel;
import com.wsq.AssistantQ.service.AssistantService;
import com.wsq.AssistantQ.service.CourseService;
import com.wsq.AssistantQ.service.EvaluationService;
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
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    //管理员新增工作考核 返回新增后的工作考核列表
    @PostMapping(value = "/admin/add")
    public Result add(@RequestBody EvaluationModel evaluationModel) {

        evaluationService.add(evaluationModel);

        //返回所有的工作考核列表
        return Result.success(findAllEvaluation().getData());
    }

    //管理员根据工作考核编号删除工作考核 返回删除后的工作考核列表
    @PostMapping(value = "/admin/deleteByObjectId")
    public Result deleteByObjectId(@RequestBody EvaluationModel evaluationModel) {
        evaluationService.deleteByObjectId(evaluationModel);

        //返回所有的工作考核列表
        return Result.success(findAllEvaluation().getData());
    }

    //管理员根据工作考核编号动态更改工作考核信息 返回更改后的工作考核列表
    @PostMapping(value = "/admin/modify")
    public Result modify(@RequestBody EvaluationModel evaluationModel) {
        evaluationService.modify(evaluationModel);

        //返回所有的工作考核列表
        return Result.success(findAllEvaluation().getData());
    }

    //管理员查找所有工作考核 返回工作考核列表
    @GetMapping(value = "/admin/findAllEvaluation")
    public Result findAllEvaluation() {
        List<EvaluationModel> list = evaluationService.findAllEvaluation();
        return Result.success(list);
    }

    //管理员根据提交者姓名查找工作考核 返回工作考核列表
    @PostMapping(value = "/admin/findByEvalSubmitterName")
    public Result findByEvalSubmitterName(@RequestBody EvaluationModel evaluationModel) {
        List<EvaluationModel> list = evaluationService.findByEvalSubmitterName(evaluationModel.getEvalSubmitterName());

        return Result.success(list);
    }

    //管理员根据工作考核审核状态查找工作考核 返回工作考核列表
    @PostMapping(value = "/admin/findByEvalStatus")
    public Result findByEvalStatus(@RequestBody EvaluationModel evaluationModel) {
        List<EvaluationModel> list = evaluationService.findByEvalStatus(evaluationModel.getEvalStatus());

        return Result.success(list);
    }

    //学生新增工作考核 返回新增后的自己的工作考核列表
    @PostMapping(value = "/student/addMy")
    public Result addMy(@RequestBody EvaluationModel evaluationModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String evalSubmitterId=userService.findByObjectId(objectId).getUserId();

        //添加提交者ID
        evaluationModel.setEvalSubmitterId(evalSubmitterId);

        //获取审核者ID 等同于课程信息表中根据课程名称（唯一的）获取提交者ID
        String evalAuditorId=courseService.findByCourName(evaluationModel.getEvalCourse()).getCourSubmitterId();

        //添加审核者ID
        evaluationModel.setEvalAuditorId(evalAuditorId);

        evaluationService.add(evaluationModel);

        //返回自己的工作考核列表
        return Result.success(findMy().getData());
    }

    //学生根据工作考核编号删除工作考核 返回删除后的自己的工作考核列表
    @PostMapping(value = "/student/deleteMyByObjectId")
    public Result deleteMyByObjectId(@RequestBody EvaluationModel evaluationModel) {
        evaluationService.deleteByObjectId(evaluationModel);

        //返回自己的工作考核列表
        return Result.success(findMy().getData());
    }

    //学生根据工作考核编号动态更改工作考核信息 返回更改后的自己的工作考核列表
    @PostMapping(value = "/student/modifyMy")
    public Result modifyMy(@RequestBody EvaluationModel evaluationModel) {
        evaluationService.modify(evaluationModel);

        //返回自己的工作考核列表
        return Result.success(findMy().getData());
    }

    //学生查看自己工作考核信息 返回自己的工作考核列表
    @GetMapping(value = "/student/findMy")
    public Result findMy() {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String evalSubmitterId=userService.findByObjectId(objectId).getUserId();

        List<EvaluationModel> list = evaluationService.findByEvalSubmitterId(evalSubmitterId);
        return Result.success(list);
    }

    //学生根据审核状态查看自己工作考核信息 返回自己的工作考核列表
    @PostMapping(value = "/student/findMyByStatus")
    public Result findMyByStatus(@RequestBody EvaluationModel evaluationModel) {
        //获取根据session获取objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取提交者ID 等同于用户信息表中获取用户ID
        String evalSubmitterId=userService.findByObjectId(objectId).getUserId();

        //根据考核审核状态和提交者ID查找工作考核
        List<EvaluationModel> list = evaluationService.findByEvalStatusAndEvalSubmitterId(evaluationModel.getEvalStatus(),evalSubmitterId);
        return Result.success(list);
    }

    //教师根据工作考核编号动态更改自己需要审核的工作考核信息 返回更改后的自己审核的工作考核列表
    @PostMapping(value = "/teacher/modifyMyAudit")
    public Result modifyMyAudit(@RequestBody EvaluationModel evaluationModel) {
        evaluationService.modify(evaluationModel);

        //返回自己审核的工作考核列表
        return Result.success(findMyAudit().getData());
    }

    //教师查看自己需要审核的工作考核信息 返回自己审核的工作考核列表
    @GetMapping(value = "/teacher/findMyAudit")
    public Result findMyAudit() {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取审核者ID 等同于用户信息表中获取用户ID
        String evalAuditorId=userService.findByObjectId(objectId).getUserId();

        List<EvaluationModel> list = evaluationService.findByEvalAuditorId(evalAuditorId);
        return Result.success(list);
    }

    //教师根据审核状态查看自己需要审核的工作考核信息 返回自己审核的工作考核列表
    @PostMapping(value = "/teacher/findMyAuditByStatus")
    public Result findMyAuditByStatus(@RequestBody EvaluationModel evaluationModel) {
        //获取根据session获取用户objectId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");

        //获取审核者ID 等同于用户信息表中获取用户ID
        String evalAuditorId=userService.findByObjectId(objectId).getUserId();

        //根据考核审核状态和审核者ID查找工作考核
        List<EvaluationModel> list = evaluationService.findByEvalStatusAndEvalAuditorId(evaluationModel.getEvalStatus(),evalAuditorId);
        return Result.success(list);
    }
}
