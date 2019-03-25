package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.AlumniInformationModel;
import com.wsq.AssistantQ.respository.AlumniInformationRepository;
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
import java.util.Map;

/**
 * @author CYann
 * @date 2018-03-01 23:12
 */
@Service
public class AlumniInformationService extends BaseService {
    @Autowired
    private AlumniInformationRepository alumniInformationRepository;
    @Autowired
    private BaseService baseService;


    //增
    public void add(AlumniInformationModel alumniInformationModel){
        if(alumniInformationModel.getStartDate().compareTo(alumniInformationModel.getEndDate()) < 0){
            baseService.add(alumniInformationRepository,alumniInformationModel);
            alumniInformationRepository.save(alumniInformationModel);
        }
        else {
            throw new MyException(ResultEnum.ERROR_112);
        }

    }

    //删
    @Transactional
    public void delete(AlumniInformationModel alumniInformationModel){
        AlumniInformationModel alumniInformationItem = alumniInformationRepository.findById(alumniInformationModel.getObjectId());
        if (alumniInformationItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(alumniInformationRepository, alumniInformationItem);
        }

    }

    //改
    public void update(AlumniInformationModel alumniInformationModel){
        AlumniInformationModel alumniInformationItem = alumniInformationRepository.findById(alumniInformationModel.getObjectId());
        if (alumniInformationItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            alumniInformationRepository.save(alumniInformationModel);
        }
    }

    //通过Id查询单个信息
    @Transactional
    public AlumniInformationModel findById(String objectId){
        return alumniInformationRepository.findById(objectId);

    }

    //查询所有校友信息
    public List<AlumniInformationModel> findAll(){
        List<AlumniInformationModel> list = alumniInformationRepository.findALLAlumniInformation();
        return list;
    }

    //学号查询所有校友信息
    public List<AlumniInformationModel> findByStuNum(String stuNumber){
        List<AlumniInformationModel> list = alumniInformationRepository.findByStuNum(stuNumber);
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> search(String stuName,String stuClass,String stuMajor,String stuNumber){
        List<Object[]> list = alumniInformationRepository.search(stuName,stuClass,stuMajor,stuNumber);
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> searchAdmin(String stuName,String stuClass){
        List<Object[]> list = alumniInformationRepository.searchAdmin(stuName,stuClass);
        return list;
    }

    //视图--学号查询校友信息
    public List<Object[]> findByStuNumber(String stuNumber){
        List<Object[]> list = alumniInformationRepository.findByStuNumber(stuNumber);
        return list;
    }

    //视图--查询所有校友信息
    public List<Object[]> findAllAlumniInformation(){
        List<Object[]> list = alumniInformationRepository.findAllAlumni();
        return list;
    }

    //视图--查询所有校友信息
    public List<Object[]> findMaxAlumni(){
        List<Object[]> list = alumniInformationRepository.findMaxAlumni();
        return list;
    }

    //视图 根据 名字 班级 毕业年份 多条件动态查询课程
    public List<Object[]> searchMaxAlumni(String stuName,String stuClass,String stuMajor,String stuNumber){
        List<Object[]> list = alumniInformationRepository.searchMaxAlumni(stuName,stuClass,stuMajor,stuNumber);
        return list;
    }



}
