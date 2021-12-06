package com.geovis.jg.common.service;


import com.geovis.jg.common.domain.Page;
import com.geovis.jg.common.domain.Tree;
import com.geovis.jg.common.mapper.BaseMapper;

import java.util.List;


public interface BaseService<T> {

    int deleteByPrimaryKey(Object key) throws Exception;

    int delete(T record) throws Exception;

    int insertSelective(T record) throws Exception;

    List<T> selectAll() throws Exception;

    T selectByPrimaryKey(Object key) throws Exception;

    int selectCount(T record) throws Exception;

    List<T> select(T record) throws Exception;

    T selectOne(T record) throws Exception;

    int updateByPrimaryKey(T record) throws Exception;

    int updateByPrimaryKeySelective(T record) throws Exception;

    List<T> insertList(List<T> t) throws Exception;

    int delete(T[] ts) throws Exception;
    int delete(List<T> ts) throws Exception;
    int deleteByPrimaryKeys(Object[] keys) throws Exception;
    int deleteByPrimaryKeys(List<Object> keys) throws Exception;

    Page select(T t, Page page) throws Exception;
    List<T> selectSelective(T t) throws Exception;

    BaseMapper<T> getMapper();
    Object getExample(T t);
    
    /**
     * 根据Example 生成查询条件 进行查询
     * @param t
     * @return
     * @throws Exception
     */
    List<T> queryByExample(T t) throws Exception;

    List<Tree> tree(T record, String xhProperty, String mcProperty) throws Exception;

    List<T> selectList(T t, Page page) throws Exception ;
	/**
	 * 武器装备 将父级 典型配备方案数据导入 子表
	 */
	//void updateByParentZbnm(T t, String parentZbnm, String zbnm, String otherPK, String otherPKName) throws Exception;
}

