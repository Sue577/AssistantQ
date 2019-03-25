package com.wsq.AssistantQ.service;

import com.wsq.AssistantQ.enums.ResultEnum;
import com.wsq.AssistantQ.exception.MyException;
import com.wsq.AssistantQ.model.AccountModel;
import com.wsq.AssistantQ.respository.AccountRepository;
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
 * @date 2018-02-28 21:18
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BaseService baseService;

    //增
    public void add(AccountModel accountModel){
        baseService.add(accountRepository,accountModel);
        accountRepository.save(accountModel);
    }

    //删
    @Transactional
    public void delete(AccountModel accountModel){
        AccountModel accountItem = accountRepository.findById(accountModel.getObjectId());
        if (accountItem ==null){
            throw new MyException(ResultEnum.ERROR_101);
        }else {
            baseService.delete(accountRepository, accountItem);
        }

    }

    //动态修改户口信息
    @Transactional
    public void update(AccountModel accountModel) {
        AccountModel accountItem = accountRepository.findById(accountModel.getObjectId());
        if (accountItem == null) {
            throw new MyException(ResultEnum.ERROR_101);
        } else {
            if(accountModel.getStuNumber() !=null && accountItem.getStuNumber().equals(accountModel.getStuNumber()) == false ){
                accountItem.setStuNumber(accountModel.getStuNumber());
            }
            if(accountModel.getAccountAddress() !=null && accountItem.getAccountAddress().equals(accountModel.getAccountAddress()) == false ){
                accountItem.setAccountAddress(accountModel.getAccountAddress());
            }
            if(accountModel.getAccountDate() !=null && accountItem.getAccountDate().equals(accountModel.getAccountDate()) == false ){
                accountItem.setAccountDate(accountModel.getAccountDate());
            }
            baseService.modify(accountRepository,accountItem);
            accountRepository.save(accountItem);
        }
    }

    //查询所有户口
    public List<AccountModel> findAll(){
        List<AccountModel> list = accountRepository.findALLAccount();
        return list;
    }

    //学号查询户口列表
    public List<AccountModel> findByStuNumber(String stuNumber){
        List<AccountModel> list = accountRepository.findByStuNumber(stuNumber);
        return list;
    }

    //根据 学号 多条件动态查询户口
    public List<AccountModel> findAllByAdvancedForm(AccountModel accountModel) {
        return accountRepository.findAll(new Specification<AccountModel>(){
            @Override
            public Predicate toPredicate(Root<AccountModel> root, CriteriaQuery<?> query, CriteriaBuilder cb){
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.isNull(root.get("delTime")));
                if(accountModel != null && !StringUtils.isEmpty(accountModel.getStuNumber()) ){
                    list.add(cb.equal(root.get("stuNumber"), accountModel.getStuNumber()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
    }

    //视图--查询所有户口信息
    public List<Object[]> listAll(){
        List<Object[]> list = accountRepository.listAll();
        return list;
    }

    //视图--查询所有户口信息
    public List<Object[]> searchAdmin(String stuName, String stuNumber){
        List<Object[]> list = accountRepository.searchAdmin(stuName,stuNumber);
        return list;
    }



}
