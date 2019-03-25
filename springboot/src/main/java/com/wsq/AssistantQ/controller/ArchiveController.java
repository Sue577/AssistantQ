package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.ArchiveModel;
import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.UserModel;
import com.wsq.AssistantQ.service.ArchiveService;
import com.wsq.AssistantQ.service.CurrentUserService;
import com.wsq.AssistantQ.service.UserService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author CYann
 * @date 2018-04-01 19:14
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;
    @Autowired
    private UserService userService;
    @Autowired
    private CurrentUserService currentUserService;

    //新增档案
    @Transactional
    @PostMapping(value = "/addarchive")
    public Result addUserArchiver(@RequestBody ArchiveModel archiveModel){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        if(archiveModel.getStuNumber().equals(item.getStuNumber()) == true){
            archiveService.add(archiveModel);
            List<ArchiveModel> list = archiveService.findByStuNumber(archiveModel.getStuNumber());
            return Result.success(list);
        } else {
            return Result.error(110,"非该用户");
        }
    }

    //展示档案
    @GetMapping(value = "/listarchive")
    public Result listUserArchive(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String objectId = (String) request.getSession().getAttribute("ID");
        CurrentUserModel item = currentUserService.findByObjectId(objectId);
        List<ArchiveModel> list = archiveService.findByStuNumber(item.getStuNumber());
        return Result.success(list);
    }

}
