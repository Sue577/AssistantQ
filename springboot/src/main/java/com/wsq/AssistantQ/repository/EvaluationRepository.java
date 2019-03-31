package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.EvaluationModel;
import com.wsq.AssistantQ.model.RecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:37
 */
@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationModel,String>, JpaSpecificationExecutor<EvaluationModel> {
    //查找所有工作考核
    @Query(value = "select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null")
    List<EvaluationModel> findAllEvaluation();

    //根据编号查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.objectId = :objectId")
    EvaluationModel findByObjectId(@Param("objectId") String objectId);

    //根据相关课程查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalCourse = ?1")
    List<EvaluationModel> findByEvalCourse(@Param("evalCourse") String evalCourse);

    //根据考核学期查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalTerm = ?1")
    List<EvaluationModel> findByEvalTerm(@Param("evalTerm") String evalTerm);

    //根据提交者姓名查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalSubmitterName = ?1")
    List<EvaluationModel> findByEvalSubmitterName(@Param("evalSubmitterName") String evalSubmitterName);

    //根据提交者ID查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalSubmitterId = ?1")
    List<EvaluationModel> findByEvalSubmitterId(@Param("evalSubmitterId") String evalSubmitterId);

    //根据考核等级查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalLevel = ?1")
    List<EvaluationModel> findByEvalLevel(@Param("evalLevel") String evalLevel);

    //根据审核者ID查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalAuditorId = ?1")
    List<EvaluationModel> findByEvalAuditorId(@Param("evalAuditorId") String evalAuditorId);

    //根据考核审核状态查找工作考核
    @Query("select evaluationModel from EvaluationModel evaluationModel where evaluationModel.delTime is null and evaluationModel.evalStatus = ?1")
    List<EvaluationModel> findByEvalStatus(@Param("evalStatus") String evalStatus);


}
