package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.RecordModel;
import com.wsq.AssistantQ.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 14:22
 */
@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BaseService baseService;

    //新增工作记录
    public void add(RecordModel recordModel) {

        //添加基础信息 编号和创建时间
        baseService.add(recordRepository, recordModel);

        //保存信息至数据库
        recordRepository.save(recordModel);
    }

    //根据记录编号删除工作记录
    @Transactional
    public void deleteByObjectId(RecordModel recordModel) {
        RecordModel recordItem = recordRepository.findByObjectId(recordModel.getObjectId());
        if (recordItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(recordRepository, recordItem);
        }
    }

    //动态修改工作记录
    @Transactional
    public void modify(RecordModel recordModel) {
        RecordModel recordItem = recordRepository.findByObjectId(recordModel.getObjectId());
        if (recordItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(recordModel.getRecoDate() !=null && recordItem.getRecoDate().equals(recordModel.getRecoDate()) == false ){
                recordItem.setRecoDate(recordModel.getRecoDate());
            }
            if(recordModel.getRecoHours() !=null && recordItem.getRecoHours().equals(recordModel.getRecoHours()) == false ){
                recordItem.setRecoHours(recordModel.getRecoHours());
            }
            if(recordModel.getRecoDesc() !=null && recordItem.getRecoDesc().equals(recordModel.getRecoDesc()) == false ){
                recordItem.setRecoDesc(recordModel.getRecoDesc());
            }
            if(recordModel.getRecoCourse() !=null && recordItem.getRecoCourse().equals(recordModel.getRecoCourse()) == false ){
                recordItem.setRecoCourse(recordModel.getRecoCourse());
            }
            if(recordModel.getRecoSubmitterName() !=null && recordItem.getRecoSubmitterName().equals(recordModel.getRecoSubmitterName()) == false ){
                recordItem.setRecoSubmitterName(recordModel.getRecoSubmitterName());
            }
            if(recordModel.getRecoSubmitterId() !=null && recordItem.getRecoSubmitterId().equals(recordModel.getRecoSubmitterId()) == false ){
                recordItem.setRecoSubmitterId(recordModel.getRecoSubmitterId());
            }
            if(recordModel.getRecoAuditorId() !=null && recordItem.getRecoAuditorId().equals(recordModel.getRecoAuditorId()) == false ){
                recordItem.setRecoAuditorId(recordModel.getRecoAuditorId());
            }
            if(recordModel.getRecoStatus() !=null && recordItem.getRecoStatus().equals(recordModel.getRecoStatus()) == false ){
                recordItem.setRecoStatus(recordModel.getRecoStatus());
            }

            baseService.modify(recordRepository,recordItem);
            recordRepository.save(recordItem);
        }
    }

    //查找所有工作记录
    public List<RecordModel> findAllRecord(){
        List<RecordModel> list = recordRepository.findAllRecord();
        return list;
    }

    //根据编号查找工作记录
    public RecordModel findByObjectId(String objectId){
        RecordModel recordModel = recordRepository.findByObjectId(objectId);
        return recordModel;
    }

    //根据工作日期查找工作记录
    public List<RecordModel> findByRecoDate(String recoDate){
        List<RecordModel> list = recordRepository.findByRecoDate(recoDate);
        return list;
    }

    //根据相关课程查找工作记录
    public List<RecordModel> findByRecoCourse(String recoCourse){
        List<RecordModel> list = recordRepository.findByRecoCourse(recoCourse);
        return list;
    }

    //根据提交者姓名查找工作记录
    public List<RecordModel> findByRecoSubmitterName(String recoSubmitterName){
        List<RecordModel> list = recordRepository.findByRecoSubmitterName(recoSubmitterName);
        return list;
    }

    //根据提交者ID查找工作记录
    public List<RecordModel> findByRecoSubmitterId(String recoSubmitterId){
        List<RecordModel> list = recordRepository.findByRecoSubmitterId(recoSubmitterId);
        return list;
    }

    //根据审核者ID查找工作记录
    public List<RecordModel> findByRecoAuditorId(String recoAuditorId){
        List<RecordModel> list = recordRepository.findByRecoAuditorId(recoAuditorId);
        return list;
    }

    //根据记录审核状态查找工作记录
    public List<RecordModel> findByRecoStatus(String recoStatus){
        List<RecordModel> list = recordRepository.findByRecoStatus(recoStatus);
        return list;
    }

    //根据记录审核状态和提交者ID查找工作记录
    public List<RecordModel> findByRecoStatusAndRecoSubmitterId(String recoStatus,String recoSubmitterId){
        List<RecordModel> list = recordRepository.findByRecoStatusAndRecoSubmitterId(recoStatus,recoSubmitterId);
        return list;
    }


    //根据记录审核状态和审核者ID查找工作记录
    public List<RecordModel> findByRecoStatusAndRecoAuditorId(String recoStatus,String recoAuditorId){
        List<RecordModel> list = recordRepository.findByRecoStatusAndRecoAuditorId(recoStatus,recoAuditorId);
        return list;
    }
}
