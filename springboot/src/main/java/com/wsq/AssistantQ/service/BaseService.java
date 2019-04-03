package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author WSQ
 * @date 2018-02-26 20:48
 */

@Service
public class BaseService {
    //删
    public void delete(JpaRepository repository, BaseEntity baseEntity) {
        Timestamp time = new Timestamp(new Date().getTime());
        baseEntity.setDelTime(time);
        repository.save(baseEntity);
    }

    //增
    public void add(JpaRepository repository, BaseEntity baseEntity) {
        Timestamp time = new Timestamp(new Date().getTime());
        baseEntity.setCreateTime(time);
        repository.save(baseEntity);
    }

    //改
    public void modify(JpaRepository repository, BaseEntity baseEntity) {
        Timestamp time = new Timestamp(new Date().getTime());
        baseEntity.setUpdateTime(time);
        repository.save(baseEntity);
    }


}
