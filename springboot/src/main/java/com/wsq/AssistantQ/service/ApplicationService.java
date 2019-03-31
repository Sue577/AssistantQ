package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.ApplicationModel;
import com.wsq.AssistantQ.model.RecruitModel;
import com.wsq.AssistantQ.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:21
 */
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private BaseService baseService;

    //新增报名信息
    public void add(ApplicationModel applicationModel) {

        //添加基础信息 编号和创建时间
        baseService.add(applicationRepository, applicationModel);

        //保存信息至数据库
        applicationRepository.save(applicationModel);
    }

    //根据报名编号删除报名信息
    @Transactional
    public void deleteByObjectId(ApplicationModel applicationModel) {
        ApplicationModel applicationItem = applicationRepository.findByObjectId(applicationModel.getObjectId());
        if (applicationItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(applicationRepository, applicationItem);
        }
    }

    //动态修改报名信息
    @Transactional
    public void modify(ApplicationModel applicationModel) {
        ApplicationModel applicationItem = applicationRepository.findByObjectId(applicationModel.getObjectId());
        if (applicationItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(applicationModel.getApplRecruitId() !=null && applicationItem.getApplRecruitId().equals(applicationModel.getApplRecruitId()) == false ){
                applicationItem.setApplRecruitId(applicationModel.getApplRecruitId());
            }
            if(applicationModel.getApplDesc() !=null && applicationItem.getApplDesc().equals(applicationModel.getApplDesc()) == false ){
                applicationItem.setApplDesc(applicationModel.getApplDesc());
            }
            if(applicationModel.getApplSubmitterName() !=null && applicationItem.getApplSubmitterName().equals(applicationModel.getApplSubmitterName()) == false ){
                applicationItem.setApplSubmitterName(applicationModel.getApplSubmitterName());
            }
            if(applicationModel.getApplSubmitterId() !=null && applicationItem.getApplSubmitterId().equals(applicationModel.getApplSubmitterId()) == false ){
                applicationItem.setApplSubmitterId(applicationModel.getApplSubmitterId());
            }
            if(applicationModel.getApplAuditorId() !=null && applicationItem.getApplAuditorId().equals(applicationModel.getApplAuditorId()) == false ){
                applicationItem.setApplAuditorId(applicationModel.getApplAuditorId());
            }
            if(applicationModel.getApplStatus() !=null && applicationItem.getApplStatus().equals(applicationModel.getApplStatus()) == false ){
                applicationItem.setApplStatus(applicationModel.getApplStatus());
            }
            baseService.modify(applicationRepository,applicationItem);
            applicationRepository.save(applicationItem);
        }
    }

    //查找所有报名
    public List<ApplicationModel> findAllApplication(){
        List<ApplicationModel> list = applicationRepository.findAllApplication();
        return list;
    }

    //根据编号查找报名信息
    public ApplicationModel findByObjectId(String objectId){
        ApplicationModel applicationModel = applicationRepository.findByObjectId(objectId);
        return applicationModel;
    }

    //根据招聘编号查找报名信息
    public List<ApplicationModel> findByApplRecruitId(String applRecruitId){
        List<ApplicationModel> list = applicationRepository.findByApplRecruitId(applRecruitId);
        return list;
    }

    //根据提交者姓名查找报名信息
    public List<ApplicationModel> findByApplSubmitterName(String applSubmitterName){
        List<ApplicationModel> list = applicationRepository.findByApplSubmitterName(applSubmitterName);
        return list;
    }

    //根据提交者ID查找报名信息
    public List<ApplicationModel> findByApplSubmitterId(String applSubmitterId){
        List<ApplicationModel> list = applicationRepository.findByApplSubmitterId(applSubmitterId);
        return list;
    }

    //根据审核者ID查找报名信息
    public List<ApplicationModel> findByApplAuditorId(String applAuditorId){
        List<ApplicationModel> list = applicationRepository.findByApplAuditorId(applAuditorId);
        return list;
    }

    //根据报名审核状态查找报名信息
    public List<ApplicationModel> findByApplStatus(String applStatus){
        List<ApplicationModel> list = applicationRepository.findByApplStatus(applStatus);
        return list;
    }

    //根据招聘编号和提交者ID查询报名信息
    public ApplicationModel findByApplRecruitIdAndApplSubmitterId(String applRecruitId,String applSubmitterId){
        ApplicationModel applicationModel = applicationRepository.findByApplRecruitIdAndApplSubmitterId(applRecruitId,applSubmitterId);
        return applicationModel;
    }
}
