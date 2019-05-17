package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.RecruitModel;
import com.wsq.AssistantQ.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:23
 */
@Service
public class RecruitService {
    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private BaseService baseService;

    //新增招聘信息
    public void add(RecruitModel recruitModel) {

        //添加基础信息 编号和创建时间
        baseService.add(recruitRepository, recruitModel);

        //保存信息至数据库
        recruitRepository.save(recruitModel);
    }

    //根据招聘编号删除招聘信息
    @Transactional
    public void deleteByObjectId(RecruitModel recruitModel) {
        RecruitModel recruitItem = recruitRepository.findByObjectId(recruitModel.getObjectId());
        if (recruitItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(recruitRepository, recruitItem);
        }
    }

    //动态修改招聘信息
    @Transactional
    public void modify(RecruitModel recruitModel) {
        RecruitModel recruitItem = recruitRepository.findByObjectId(recruitModel.getObjectId());
        if (recruitItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(recruitModel.getRecrSubmitterId() !=null && recruitItem.getRecrSubmitterId().equals(recruitModel.getRecrSubmitterId()) == false ){
                recruitItem.setRecrSubmitterId(recruitModel.getRecrSubmitterId());
            }
            if(recruitModel.getRecrCourse() !=null && recruitItem.getRecrCourse().equals(recruitModel.getRecrCourse()) == false ){
                recruitItem.setRecrCourse(recruitModel.getRecrCourse());
            }
            if(recruitModel.getRecrDesc() !=null && recruitItem.getRecrDesc().equals(recruitModel.getRecrDesc()) == false ){
                recruitItem.setRecrDesc(recruitModel.getRecrDesc());
            }
            if(recruitModel.getRecrSubmitterName() !=null && recruitItem.getRecrSubmitterName().equals(recruitModel.getRecrSubmitterName()) == false ){
                recruitItem.setRecrSubmitterName(recruitModel.getRecrSubmitterName());
            }
            if(recruitModel.getRecrDeadLine() !=null && recruitItem.getRecrDeadLine().equals(recruitModel.getRecrDeadLine()) == false ){
                recruitItem.setRecrDeadLine(recruitModel.getRecrDeadLine());
            }
            baseService.modify(recruitRepository,recruitItem);
            recruitRepository.save(recruitItem);
        }
    }

    //查找所有招聘
    public List<RecruitModel> findAllRecruit(){
        List<RecruitModel> list = recruitRepository.findAllRecruit();
        return list;
    }

    //根据编号查找招聘信息
    public RecruitModel findByObjectId(String objectId){
        RecruitModel recruitModel = recruitRepository.findByObjectId(objectId);
        return recruitModel;
    }

    //根据课程名称查找招聘信息
    public List<RecruitModel> findByRecrCourse(String recrCourse){
        List<RecruitModel> list = recruitRepository.findByRecrCourse(recrCourse);
        return list;
    }

    //根据招聘信息标题查找招聘信息
    public List<RecruitModel> findByRecrTitle(String recrTitle){
        List<RecruitModel> list = recruitRepository.findByRecrTitle(recrTitle);
        return list;
    }

    //根据提交者ID查找招聘信息
    public List<RecruitModel> findByRecrSubmitterId(String recrSubmitterId){
        List<RecruitModel> list = recruitRepository.findByRecrSubmitterId(recrSubmitterId);
        return list;
    }
}
