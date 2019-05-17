package com.wsq.AssistantQ.repository;

import com.wsq.AssistantQ.model.MessageModel;
import com.wsq.AssistantQ.model.RecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WSQ
 * @date 2019/3/28 0:38
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageModel,String>, JpaSpecificationExecutor<MessageModel> {
    //查找所有通知信息
    @Query(value = "select messageModel from MessageModel messageModel where messageModel.delTime is null")
    List<MessageModel> findAllMessage();

    //根据编号查找通知信息
    @Query("select messageModel from MessageModel messageModel where messageModel.delTime is null and messageModel.objectId = ?1")
    MessageModel findByObjectId(@Param("objectId") String objectId);

    //根据发送人ID查找通知信息
    @Query("select messageModel from MessageModel messageModel where messageModel.delTime is null and messageModel.msgSenderId = ?1")
    List<MessageModel> findByMsgSenderId(@Param("msgSenderId") String msgSenderId);

    //根据接收人ID查找通知信息
    @Query("select messageModel from MessageModel messageModel where messageModel.delTime is null and messageModel.msgReceiverId = ?1")
    List<MessageModel> findByMsgReceiverId(@Param("msgReceiverId") String msgReceiverId);

    //根据状态查看查找通知信息
    @Query("select messageModel from MessageModel messageModel where messageModel.delTime is null and messageModel.msgStatus = ?1")
    List<MessageModel> findByMsgStatus(@Param("msgStatus") String msgStatus);

    //根据查看状态和接收人ID查找通知信息
    @Query("select messageModel from MessageModel messageModel where messageModel.delTime is null and messageModel.msgStatus = ?1 and messageModel.msgReceiverId = ?2")
    List<MessageModel> findByMsgStatusAndMsgReceiverId(@Param("msgStatus") String msgStatus,@Param("msgReceiverId") String msgReceiverId);
}
