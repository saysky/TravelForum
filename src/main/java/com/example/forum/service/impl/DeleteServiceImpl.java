package com.example.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.forum.entity.*;
import com.example.forum.enums.CommentTypeEnum;
import com.example.forum.mapper.*;
import com.example.forum.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author saysky
 * @date 2021/3/20
 */
@Service
public class DeleteServiceImpl implements DeleteService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleRefMapper userRoleRefMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionMarkRefMapper questionMarkRefMapper;

    @Autowired
    private QuestionLikeRefMapper questionLikeRefMapper;

    @Autowired
    private QuestionNoticeRefMapper questionNoticeRefMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {

        // 1.删除文章
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("user_id", id);
        List<Post> postList = postMapper.selectList(postQueryWrapper);
        for (Post post : postList) {
            this.deletePost(post.getId());
        }

        // 2.删除帖子
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("user_id", id);
        List<Question> questionList = questionMapper.selectList(questionQueryWrapper);
        for (Question question : questionList) {
            this.deleteQuestion(question.getId());
        }

        // 3.删除用户
        userMapper.deleteById(id);

        // 4.删除角色关联
        QueryWrapper<UserRoleRef> userRoleRefQueryWrapper = new QueryWrapper<>();
        userRoleRefQueryWrapper.eq("user_id", id);
        userRoleRefMapper.delete(userRoleRefQueryWrapper);

        // 5.删除关注
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("user_id", id).or().eq("accept_user_id", id);
        followMapper.delete(followQueryWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(Long id) {
        // 1.删除回帖
        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
        answerQueryWrapper.eq("question_id", id);
        List<Answer> answers = answerMapper.selectList(answerQueryWrapper);
        for (Answer answer : answers) {
            this.deleteAnswer(answer.getId());
        }

        // 2.删除帖子
        questionMapper.deleteById(id);

        // 3.删除帖子通知
        QueryWrapper<QuestionNoticeRef> questionNoticeRefQueryWrapper = new QueryWrapper<>();
        questionNoticeRefQueryWrapper.eq("question_id", id);
        questionNoticeRefMapper.delete(questionNoticeRefQueryWrapper);

        // 4.删除帖子点赞
        QueryWrapper<QuestionLikeRef> questionLikeRefQueryWrapper = new QueryWrapper<>();
        questionLikeRefQueryWrapper.eq("question_id", id);
        questionLikeRefMapper.delete(questionLikeRefQueryWrapper);

        // 5.删除帖子收藏
        QueryWrapper<QuestionMarkRef> questionMarkRefQueryWrapper = new QueryWrapper<>();
        questionMarkRefQueryWrapper.eq("question_id", id);
        questionMarkRefMapper.delete(questionMarkRefQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAnswer(Long id) {
        Answer answer = answerMapper.selectById(id);
        if (answer != null) {
            Question question = questionMapper.selectById(answer.getQuestionId());
            // 1.删除回帖
            answerMapper.deleteById(id);

            // 2.修改帖子的回帖数
            if (question != null) {
                QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
                answerQueryWrapper.eq("question_id", question.getId());
                List<Answer> answerList = answerMapper.selectList(answerQueryWrapper);
                question.setAnswerCount(answerList.size());
                questionMapper.updateById(question);
            }

            // 3.删除评论
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("business_id", id);
            commentQueryWrapper.eq("type", CommentTypeEnum.ANSWER.getCode());
            commentMapper.delete(commentQueryWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long id) {
        // 1. 删除文章
        postMapper.deleteById(id);

        // 4.删除评论
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("business_id", id);
        commentQueryWrapper.eq("type", CommentTypeEnum.POST.getCode());
        commentMapper.delete(commentQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 1.删除用户关联
        QueryWrapper<UserRoleRef> userRoleRefQueryWrapper = new QueryWrapper<>();
        userRoleRefQueryWrapper.eq("role_id", id);
        userRoleRefMapper.delete(userRoleRefQueryWrapper);

        // 2.删除权限关联
        QueryWrapper<RolePermissionRef> rolePermissionRefQueryWrapper = new QueryWrapper<>();
        rolePermissionRefQueryWrapper.eq("role_id", id);
        rolePermissionRefMapper.delete(rolePermissionRefQueryWrapper);

        // 3.删除角色
        roleMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long id) {
        // 1.删除关联
        QueryWrapper<RolePermissionRef> rolePermissionRefQueryWrapper = new QueryWrapper<>();
        rolePermissionRefQueryWrapper.eq("permission_id", id);
        rolePermissionRefMapper.delete(rolePermissionRefQueryWrapper);

        // 2.删除权限
        permissionMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 1.删除分类
        categoryMapper.deleteById(id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment != null) {
            // 1.删除评论
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("comment_parent", id);
            List<Comment> childList = commentMapper.selectList(commentQueryWrapper);
            if (childList != null && childList.size() > 0) {
                for (Comment c : childList) {
                    this.deleteComment(c.getId());
                }
            }

            // 2.删除评论
            commentMapper.deleteById(id);

            // 3.更新所属文章/回帖评论数
            if (CommentTypeEnum.POST.getCode().equals(comment.getType())) {
                Post post = postMapper.selectById(comment.getBusinessId());
                if (post != null) {
                    QueryWrapper<Comment> commentQueryWrapper2 = new QueryWrapper<>();
                    commentQueryWrapper2.eq("business_id", post.getId());
                    commentQueryWrapper2.eq("type", CommentTypeEnum.POST.getCode());
                    List<Comment> commentList = commentMapper.selectList(commentQueryWrapper2);
                    post.setCommentSize(commentList.size());
                    postMapper.updateById(post);
                }
            } else if (CommentTypeEnum.ANSWER.getCode().equals(comment.getType())) {
                Answer answer = answerMapper.selectById(comment.getBusinessId());
                if (answer != null) {
                    QueryWrapper<Comment> commentQueryWrapper2 = new QueryWrapper<>();
                    commentQueryWrapper2.eq("business_id", answer.getId());
                    commentQueryWrapper2.eq("type", CommentTypeEnum.ANSWER.getCode());
                    List<Comment> commentList = commentMapper.selectList(commentQueryWrapper2);
                    answer.setCommentCount(commentList.size());
                    answerMapper.updateById(answer);
                }
            }
        }
    }
}

