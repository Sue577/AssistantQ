package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.RedArchiveModel;
import com.wsq.AssistantQ.respository.RedArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author CYann
 * @date 2018-03-01 22:56
 */
@Service
public class RedArchiveService extends BaseService {
    @Autowired
    private RedArchiveRepository redArchiveRepository;
    @Autowired
    private BaseService baseService;

    //增
    public void add(RedArchiveModel redArchiveModel){
        baseService.add(redArchiveRepository, redArchiveModel);
        redArchiveRepository.save(redArchiveModel);
    }

    //删
    @Transactional
    public void delete(RedArchiveModel redArchiveModel){
        RedArchiveModel redArchiveItem = redArchiveRepository.findById(redArchiveModel.getObjectId());
        if (redArchiveItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(redArchiveRepository, redArchiveItem);
        }

    }
    //动态修改红色档案信息
    @Transactional
    public void update(RedArchiveModel redArchiveModel) {
        RedArchiveModel redArchiveItem = redArchiveRepository.findById(redArchiveModel.getObjectId());
        if (redArchiveItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(redArchiveModel.getStuNumber() !=null && redArchiveItem.getStuNumber().equals(redArchiveModel.getStuNumber()) == false ){
                redArchiveItem.setStuNumber(redArchiveModel.getStuNumber());
            }
            if(redArchiveModel.getJoinDate() !=null && redArchiveItem.getJoinDate().equals(redArchiveModel.getJoinDate()) == false ){
                redArchiveItem.setJoinDate(redArchiveModel.getJoinDate());
            }
            if(redArchiveModel.getActivistDate() !=null && redArchiveItem.getActivistDate().equals(redArchiveModel.getActivistDate()) == false ){
                redArchiveItem.setActivistDate(redArchiveModel.getActivistDate());
            }
            if(redArchiveModel.getIntroducer() !=null && redArchiveItem.getIntroducer().equals(redArchiveModel.getIntroducer()) == false ){
                redArchiveItem.setIntroducer(redArchiveModel.getIntroducer());
            }
            if(redArchiveModel.getIntroducerB() !=null && redArchiveItem.getIntroducerB().equals(redArchiveModel.getIntroducerB()) == false ){
                redArchiveItem.setIntroducerB(redArchiveModel.getIntroducerB());
            }
            baseService.modify(redArchiveRepository, redArchiveItem);
            redArchiveRepository.save(redArchiveItem);
        }
    }
    //学号查询红色档案列表
    public List<RedArchiveModel> findByStuNumber(String stuNumber){
        List<RedArchiveModel> list = redArchiveRepository.findByStuNumber(stuNumber);
        return list;
    }


    //查询所有红色档案
    public List<RedArchiveModel> findALL(){
        List<RedArchiveModel> list = redArchiveRepository.findALLRedArchive();
        return list;
    }

    //视图--查询所有户口信息
    public List<Object[]> listAll(){
        List<Object[]> list = redArchiveRepository.listAll();
        return list;
    }

    //视图--查询所有户口信息
    public List<Object[]> searchAdmin(String stuName, String stuNumber){
        List<Object[]> list = redArchiveRepository.searchAdmin(stuName,stuNumber);
        return list;
    }

}
