package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.ChatGroupModel;
import com.wsq.AssistantQ.respository.ChatGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CYann
 * @date 2018-04-15 22:20
 */
@Service
public class ChatGroupService {
    @Autowired
    private ChatGroupRepository chatGroupRepository;
    @Autowired
    private BaseService baseService;

    //增
    public void add(ChatGroupModel chatGroupModel){
        baseService.add(chatGroupRepository,chatGroupModel);
        chatGroupRepository.save(chatGroupModel);
    }

    //删
    @Transactional
    public void delete(ChatGroupModel chatGroupModel){
        ChatGroupModel chatGroupItem = chatGroupRepository.findById(chatGroupModel.getObjectId());
        if (chatGroupItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(chatGroupRepository, chatGroupItem);
        }

    }

    //动态修改交流组信息
    @Transactional
    public void update(ChatGroupModel chatGroupModel) {
        ChatGroupModel chatGroupItem = chatGroupRepository.findById(chatGroupModel.getObjectId());
        if (chatGroupItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(chatGroupModel.getStuMajor() !=null && chatGroupItem.getStuMajor().equals(chatGroupModel.getStuMajor()) == false ){
                chatGroupItem.setStuMajor(chatGroupModel.getStuMajor());
            }
            if(chatGroupModel.getStuEndYear() !=null && chatGroupItem.getStuEndYear().equals(chatGroupModel.getStuEndYear()) == false ){
                chatGroupItem.setStuEndYear(chatGroupModel.getStuEndYear());
            }
            if(chatGroupModel.getQqNo() !=null && chatGroupItem.getQqNo().equals(chatGroupModel.getQqNo()) == false ){
                chatGroupItem.setQqNo(chatGroupModel.getQqNo());
            }
            baseService.modify(chatGroupRepository,chatGroupItem);
            chatGroupRepository.save(chatGroupItem);
        }
    }

    //根据专业和毕业时间查询交流群
    public ChatGroupModel findByStuMajorAndAndStuEndYear(String stuMajor, String stuEndYear){
        return chatGroupRepository.findByStuMajorAndAndStuEndYear(stuMajor, stuEndYear);
    }

    //根据 专业和毕业时间 多条件动态查询交流群
    public List<ChatGroupModel> findAllByAdvancedForm(ChatGroupModel chatGroupModel) {
        return chatGroupRepository.findAll(new Specification<ChatGroupModel>(){
            @Override
            public Predicate toPredicate(Root<ChatGroupModel> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.isNull(root.get("delTime")));
                if(chatGroupModel != null && !StringUtils.isEmpty(chatGroupModel.getStuMajor()) ){
                    list.add(cb.equal(root.get("stuMajor"), chatGroupModel.getStuMajor()));
                }
                if(chatGroupModel != null && !StringUtils.isEmpty(chatGroupModel.getStuEndYear()) ){
                    list.add(cb.equal(root.get("stuEndYear"), chatGroupModel.getStuEndYear()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }

    //查询所有档案
    public List<ChatGroupModel>findAll(){
        List<ChatGroupModel> list = chatGroupRepository.findALL();
        return list;
    }


}
