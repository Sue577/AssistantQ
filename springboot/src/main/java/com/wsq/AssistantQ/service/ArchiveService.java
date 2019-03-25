package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.ArchiveModel;
import com.wsq.AssistantQ.respository.AccountRepository;
import com.wsq.AssistantQ.respository.ArchiveRepository;
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
 * @date 2018-03-01 22:55
 */
@Service
public class ArchiveService extends BaseService {
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private BaseService baseService;

    //增
    public void add(ArchiveModel archiveModel){
        baseService.add(archiveRepository,archiveModel);
        archiveRepository.save(archiveModel);
    }

    //删
    @Transactional
    public void delete(ArchiveModel archiveModel){
        ArchiveModel archiveItem = archiveRepository.findById(archiveModel.getObjectId());
        if (archiveItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(archiveRepository, archiveItem);
        }

    }

    //动态修改档案信息
    @Transactional
    public void update(ArchiveModel archiveModel) {
        ArchiveModel archiveItem = archiveRepository.findById(archiveModel.getObjectId());
        if (archiveItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(archiveModel.getStuNumber() !=null && archiveItem.getStuNumber().equals(archiveModel.getStuNumber()) == false ){
                archiveItem.setStuNumber(archiveModel.getStuNumber());
            }
            if(archiveModel.getUnit() !=null && archiveItem.getUnit().equals(archiveModel.getUnit()) == false ){
                archiveItem.setUnit(archiveModel.getUnit());
            }
            if(archiveModel.getUnitAddress() !=null && archiveItem.getUnitAddress().equals(archiveModel.getUnitAddress()) == false ){
                archiveItem.setUnitAddress(archiveModel.getUnitAddress());
            }
            if(archiveModel.getFlowDate() !=null && archiveItem.getFlowDate().equals(archiveModel.getFlowDate()) == false ){
                archiveItem.setFlowDate(archiveModel.getFlowDate());
            }
            baseService.modify(archiveRepository,archiveItem);
            archiveRepository.save(archiveItem);
        }
    }

    //通过学号查询所有档案
    public List<ArchiveModel> findByStuNumber(String stuNumber){
        List<ArchiveModel> list = archiveRepository.findByStuNumber(stuNumber);
        return list;
    }

    //通过学号查询所有档案
    public List<ArchiveModel> findAll(){
        List<ArchiveModel> list = archiveRepository.findALLArchive();
        return list;
    }

    //根据学号和目前单位查询档案
    public ArchiveModel findByStuNumberAndUnit(String stuNumber, String unit){
        return archiveRepository.findByStuNumberAndUnit(stuNumber, unit);
    }

    //根据 学号 目前单位 多条件动态查询课程
    public List<ArchiveModel> findAllByAdvancedForm(ArchiveModel archiveModel) {
        return archiveRepository.findAll(new Specification<ArchiveModel>(){
            @Override
            public Predicate toPredicate(Root<ArchiveModel> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.isNull(root.get("delTime")));
                if(archiveModel != null && !StringUtils.isEmpty(archiveModel.getStuNumber()) ){
                    list.add(cb.equal(root.get("stuNumber"), archiveModel.getStuNumber()));
                }
                if(archiveModel != null && !StringUtils.isEmpty(archiveModel.getUnit()) ){
                    list.add(cb.equal(root.get("unit"), archiveModel.getUnit()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }

    //视图--查询所有档案信息
    public List<Object[]> listAll(){
        List<Object[]> list = archiveRepository.listAll();
        return list;
    }

    //视图--查询所有档案信息
    public List<Object[]> searchAdmin(String stuName, String stuNumber){
        List<Object[]> list = archiveRepository.searchAdmin(stuName,stuNumber);
        return list;
    }

}
