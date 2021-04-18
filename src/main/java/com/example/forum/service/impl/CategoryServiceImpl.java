package com.example.forum.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.forum.entity.Category;
import com.example.forum.mapper.CategoryMapper;
import com.example.forum.mapper.PostMapper;
import com.example.forum.mapper.QuestionMapper;
import com.example.forum.service.CategoryService;
import com.example.forum.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *     分类业务逻辑实现类
 * </pre>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private DeleteService deleteService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private QuestionMapper questionMapper;


    @Override
    public BaseMapper<Category> getRepository() {
        return categoryMapper;
    }

    @Override
    public QueryWrapper<Category> getQueryWrapper(Category category) {
        //对指定字段查询
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (category != null) {
            if (StrUtil.isNotBlank(category.getCateName())) {
                queryWrapper.like("cate_name", category.getCateName());
            }
            if (StrUtil.isNotBlank(category.getCateType())) {
                queryWrapper.like("cate_type", category.getCateType());
            }
        }
        return queryWrapper;
    }

    @Override
    public Category insert(Category category) {
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public Category update(Category category) {
        categoryMapper.updateById(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        deleteService.deleteCategory(id);
    }


    @Override
    public Integer countPostByCateId(Long cateId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cate_id", cateId);
        return postMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer countQuestionByCateId(Long cateId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cate_id", cateId);
        return questionMapper.selectCount(queryWrapper);
    }

    @Override
    public Category insertOrUpdate(Category entity) {
        if (entity.getId() == null) {
            insert(entity);
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return categoryMapper.deleteByUserId(userId);
    }


    @Override
    public List<Category> findByCateType(String cateType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cate_type", cateType);
        return categoryMapper.selectList(queryWrapper);
    }
}
