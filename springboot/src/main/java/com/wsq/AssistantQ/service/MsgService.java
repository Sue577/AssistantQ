package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.CurrentUserModel;
import com.wsq.AssistantQ.model.MsgModel;
import com.wsq.AssistantQ.respository.MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CYann
 * @date 2018-04-27 22:45
 */
@Service
public class MsgService {
    @Autowired
    private MsgRepository msgRepository;
    @Autowired
    private BaseService baseService;


    //增
    public void addFeedback(MsgModel msg){
        msg.setRecUser("SuperAdmin");
        msg.setMsgStats("unread");
        msg.setMsgType("消息");
        baseService.add(msgRepository,msg);
        msgRepository.save(msg);
    }

    //增加通知
    public void addBoard(MsgModel msg){
        msg.setRecUser("SuperAdmin");
        msg.setSendUser("SuperAdmin");
        msg.setMsgStats("unread");
        msg.setMsgType("通知");
        baseService.add(msgRepository,msg);
        msgRepository.save(msg);
    }

    //增加回复
    public void addReply(MsgModel msg){
        MsgModel msgItem = msgRepository.findById(msg.getObjectId());
        if(msgItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        } else{
            msg.setRecUser(msgItem.getSendUser());
            msg.setSendUser("SuperAdmin");
            msg.setMsgStats("unread");
            msg.setMsgType("消息");
            baseService.add(msgRepository,msg);
            msgRepository.save(msg);
        }
    }

    //删
    public void delete(MsgModel msg){
        MsgModel msgItem = msgRepository.findById(msg.getObjectId());
        if(msgItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        } else{
            msgItem.setMsgStats("read");
            baseService.delete(msgRepository, msgItem);
        }
    }
    //改
    public void update(MsgModel msg){
        MsgModel msgItem = msgRepository.findById(msg.getObjectId());
        if(msgItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        } else{
            msgItem.setMsgContent(msg.getMsgContent());
            baseService.modify(msgRepository, msgItem);
            msgRepository.save(msgItem);
        }
    }
    //改状态
    public void updateStatus(MsgModel msg){
        MsgModel msgItem = msgRepository.findById(msg.getObjectId());
        if(msgItem == null){
            throw new MyException(ResultEnum.ERROR_101);
        } else{
            msgItem.setMsgStats("read");
            baseService.modify(msgRepository, msgItem);
            msgRepository.save(msgItem);
        }
    }
    //查询所有
    public List<MsgModel> findAll(){
        List<MsgModel> list = msgRepository.findAll();
        return  list;
    }
    //主key查询
    public  MsgModel findById(String id){
        return msgRepository.findById(id);
    }
    //用户id查询
    public List<MsgModel> findByRecUser(String userId){
        return msgRepository.findByRecUser(userId);
    }
}
