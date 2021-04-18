package com.example.forum.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.forum.common.base.BaseService;
import com.example.forum.entity.Category;

import java.util.List;

/**
 * <pre>
 *     分类业务逻辑接口
 * </pre>
 *
 * @author saysky
 * @date 2021/3/20
 */
public interface CategoryService extends BaseService<Category, Long> {


    /**
     * 获得某个分类的所有文章数
     *
     * @param cateId 分类Id
     * @return 文章数
     */
    Integer countPostByCateId(Long cateId);

    /**
     * 获得某个分类的所有帖子数
     *
     * @param cateId 分类Id
     * @return 文章数
     */
    Integer countQuestionByCateId(Long cateId);

    /**
     * 根据用户ID删除
     *
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);

    /**
     * 根据类型查询
     *
     * @param cateType
     * @return
     */
    List<Category> findByCateType(String cateType);
}
