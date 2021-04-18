package com.example.forum.service;

/**
 * 删除功能
 * 因为毕竟复杂，单独抽出来
 * @author saysky
 * @date 2021/3/20
 */

public interface DeleteService {

    void deleteUser(Long id);

    void deleteQuestion(Long id);

    void deleteAnswer(Long id);

    void deletePost(Long id);

    void deleteRole(Long id);

    void deletePermission(Long id);

    void deleteCategory(Long id);

    void deleteComment(Long id);
}
