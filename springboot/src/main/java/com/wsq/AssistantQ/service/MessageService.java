package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.MessageModel;
import com.wsq.AssistantQ.repository.MessageRepository;
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
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private BaseService baseService;

    //新增通知信息
    public void add(MessageModel messageModel) {

        //添加基础信息 编号和创建时间
        baseService.add(messageRepository, messageModel);

        //保存信息至数据库
        messageRepository.save(messageModel);
    }

    //根据通知编号删除通知信息
    @Transactional
    public void deleteByObjectId(MessageModel messageModel) {
        MessageModel messageItem = messageRepository.findByObjectId(messageModel.getObjectId());
        if (messageItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            baseService.delete(messageRepository, messageItem);
        }
    }

    //动态修改通知信息
    @Transactional
    public void modify(MessageModel messageModel) {
        MessageModel messageItem = messageRepository.findByObjectId(messageModel.getObjectId());
        if (messageItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(messageModel.getMsgSenderId() !=null && messageItem.getMsgSenderId().equals(messageModel.getMsgSenderId()) == false ){
                messageItem.setMsgSenderId(messageModel.getMsgSenderId());
            }
            if(messageModel.getMsgReceiverId() !=null && messageItem.getMsgReceiverId().equals(messageModel.getMsgReceiverId()) == false ){
                messageItem.setMsgReceiverId(messageModel.getMsgReceiverId());
            }
            if(messageModel.getMsgDesc() !=null && messageItem.getMsgDesc().equals(messageModel.getMsgDesc()) == false ){
                messageItem.setMsgDesc(messageModel.getMsgDesc());
            }
            baseService.modify(messageRepository,messageItem);
            messageRepository.save(messageItem);
        }
    }

    //查找所有通知信息
    public List<MessageModel> findAllMessage(){
        List<MessageModel> list = messageRepository.findAllMessage();
        return list;
    }

    //根据编号查找通知信息
    public MessageModel findByObjectId(String objectId){
        MessageModel messageModel = messageRepository.findByObjectId(objectId);
        return messageModel;
    }

    //根据发送人ID查找通知信息
    public List<MessageModel> findByMsgSenderId(String msgSenderId){
        List<MessageModel> list = messageRepository.findByMsgSenderId(msgSenderId);
        return list;
    }

    //根据接收人ID查找通知信息
    public List<MessageModel> findByMsgReceiverId(String msgReceiverId){
        List<MessageModel> list = messageRepository.findByMsgReceiverId(msgReceiverId);
        return list;
    }
}
