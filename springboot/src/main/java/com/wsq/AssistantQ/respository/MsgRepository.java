package com.wsq.AssistantQ.respository;

import com.wsq.AssistantQ.model.MsgModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.sql.Timestamp;
/**
 * @author CYann
 * @date 2018-05-05 21:30
 */
public interface MsgRepository extends JpaRepository<MsgModel,String>, JpaSpecificationExecutor<MsgModel> {
    @Query("select msgModel from MsgModel msgModel")
    List<MsgModel> findAll();

    @Query("select msgModel from MsgModel msgModel where msgModel.objectId = :objectId and msgModel.delTime is null")
    MsgModel findById(@Param("objectId") String objectId);

    //根据用户id查找用户的消息
    @Query("select msgModel from MsgModel msgModel where msgModel.recUser = ?1 or msgModel.msgType = '通知' and msgModel.delTime is null ")
    List<MsgModel> findByRecUser(@Param("recUser") String recUser);

}
