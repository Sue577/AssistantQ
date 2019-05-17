package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.EvaluationModel;
import com.wsq.AssistantQ.model.RecordModel;
import com.wsq.AssistantQ.repository.EvaluationRepository;
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
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private BaseService baseService;

    //新增工作考核
    public void add(EvaluationModel evaluationModel) {

        //添加基础信息 编号和创建时间
        baseService.add(evaluationRepository, evaluationModel);

        //保存信息至数据库
        evaluationRepository.save(evaluationModel);
    }

    //根据考核编号删除工作考核
    @Transactional
    public void deleteByObjectId(EvaluationModel evaluationModel) {
        EvaluationModel evaluationItem = evaluationRepository.findByObjectId(evaluationModel.getObjectId());
        if (evaluationItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(evaluationRepository, evaluationItem);
        }
    }

    //动态修改工作考核
    @Transactional
    public void modify(EvaluationModel evaluationModel) {
        EvaluationModel evaluationItem = evaluationRepository.findByObjectId(evaluationModel.getObjectId());
        if (evaluationItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(evaluationModel.getEvalCourse() !=null && evaluationItem.getEvalCourse().equals(evaluationModel.getEvalCourse()) == false ){
                evaluationItem.setEvalCourse(evaluationModel.getEvalCourse());
            }
            if(evaluationModel.getEvalTerm() !=null && evaluationItem.getEvalTerm().equals(evaluationModel.getEvalTerm()) == false ){
                evaluationItem.setEvalTerm(evaluationModel.getEvalTerm());
            }
            if(evaluationModel.getEvalDesc() !=null && evaluationItem.getEvalDesc().equals(evaluationModel.getEvalDesc()) == false ){
                evaluationItem.setEvalDesc(evaluationModel.getEvalDesc());
            }
            if(evaluationModel.getEvalSubmitterName() !=null && evaluationItem.getEvalSubmitterName().equals(evaluationModel.getEvalSubmitterName()) == false ){
                evaluationItem.setEvalSubmitterName(evaluationModel.getEvalSubmitterName());
            }
            if(evaluationModel.getEvalSubmitterId() !=null && evaluationItem.getEvalSubmitterId().equals(evaluationModel.getEvalSubmitterId()) == false ){
                evaluationItem.setEvalSubmitterId(evaluationModel.getEvalSubmitterId());
            }
            if(evaluationModel.getEvalLevel() !=null && evaluationItem.getEvalLevel().equals(evaluationModel.getEvalLevel()) == false ){
                evaluationItem.setEvalLevel(evaluationModel.getEvalLevel());
            }
            if(evaluationModel.getEvalAuditorId() !=null && evaluationItem.getEvalAuditorId().equals(evaluationModel.getEvalAuditorId()) == false ){
                evaluationItem.setEvalAuditorId(evaluationModel.getEvalAuditorId());
            }
            if(evaluationModel.getEvalStatus() !=null && evaluationItem.getEvalStatus().equals(evaluationModel.getEvalStatus()) == false ){
                evaluationItem.setEvalStatus(evaluationModel.getEvalStatus());
            }
            baseService.modify(evaluationRepository,evaluationItem);
            evaluationRepository.save(evaluationItem);
        }
    }

    //查找所有工作考核
    public List<EvaluationModel> findAllEvaluation(){
        List<EvaluationModel> list = evaluationRepository.findAllEvaluation();
        return list;
    }

    //根据编号查找工作考核
    public EvaluationModel findByObjectId(String objectId){
        EvaluationModel evaluationModel = evaluationRepository.findByObjectId(objectId);
        return evaluationModel;
    }

    //根据相关课程查找工作考核
    public List<EvaluationModel> findByEvalCourse(String evalCourse){
        List<EvaluationModel> list = evaluationRepository.findByEvalCourse(evalCourse);
        return list;
    }

    //根据考核学期查找工作考核
    public List<EvaluationModel> findByEvalTerm(String evalTerm){
        List<EvaluationModel> list = evaluationRepository.findByEvalTerm(evalTerm);
        return list;
    }

    //根据提交者姓名查找工作考核
    public List<EvaluationModel> findByEvalSubmitterName(String evalSubmitterName){
        List<EvaluationModel> list = evaluationRepository.findByEvalSubmitterName(evalSubmitterName);
        return list;
    }

    //根据提交者ID查找工作考核
    public List<EvaluationModel> findByEvalSubmitterId(String evalSubmitterId){
        List<EvaluationModel> list = evaluationRepository.findByEvalSubmitterId(evalSubmitterId);
        return list;
    }

    //根据考核等级查找工作考核
    public List<EvaluationModel> findByEvalLevel(String evalLevel){
        List<EvaluationModel> list = evaluationRepository.findByEvalLevel(evalLevel);
        return list;
    }

    //根据审核者ID查找工作考核
    public List<EvaluationModel> findByEvalAuditorId(String evalAuditorId){
        List<EvaluationModel> list = evaluationRepository.findByEvalAuditorId(evalAuditorId);
        return list;
    }

    //根据考核审核状态查找工作考核
    public List<EvaluationModel> findByEvalStatus(String evalStatus){
        List<EvaluationModel> list = evaluationRepository.findByEvalStatus(evalStatus);
        return list;
    }

    //根据考核审核状态和提交者ID查找工作考核
    public List<EvaluationModel> findByEvalStatusAndEvalSubmitterId(String evalStatus,String evalSubmitterId){
        List<EvaluationModel> list = evaluationRepository.findByEvalStatusAndEvalSubmitterId(evalStatus,evalSubmitterId);
        return list;
    }

    //根据考核审核状态和审核者ID查找工作考核
    public List<EvaluationModel> findByEvalStatusAndEvalAuditorId(String evalStatus,String evalAuditorId){
        List<EvaluationModel> list = evaluationRepository.findByEvalStatusAndEvalAuditorId(evalStatus,evalAuditorId);
        return list;
    }
}
