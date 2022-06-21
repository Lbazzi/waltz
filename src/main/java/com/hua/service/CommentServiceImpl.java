package com.hua.service;

import com.hua.entity.Comment;
import com.hua.mapper.CommentMapper;
import com.hua.vo.CommentQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listComments(Long blogId) {

        List<Comment> comments = commentMapper.listComments(blogId); // 获取所有顶级评论
        List<Comment> comments1 = eachComment(comments);
        System.out.println("这是    " + comments1);
        return comments1;
    }

    @Override
    public List<Comment> listAllComments() {
        return commentMapper.listAllComments();
    }

    @Override
    public List<Comment> queryAllComments(CommentQuery comment) {
        return commentMapper.queryAllCommentsByQuery(comment);
    }

    //返回给后台
    @Override
    public List<Comment> listAdminComments() {
        return commentMapper.listAdminComments();
    }

    // 循环每个顶级评论的节点
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c); // 把原有评论数据复制一份到新集合 避免对原先数据的修改 对数据库里的数据产生变化
        }
        // 合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public int saveComments(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        System.out.println("父级评论id为" + parentCommentId);
        if (parentCommentId != -1) {
            System.out.println("这里是  " + commentMapper.getCommentByParentId(parentCommentId));
            comment.setParentComment(commentMapper.getCommentByParentId(parentCommentId));
        } else {
            comment.setParentComment(null);
        }

        comment.setCreateTime(new Date());
        return commentMapper.saveComments(comment);
    }

    @Override
    public int deleteComments(Long id) {
        return commentMapper.deleteComments(id);
    }
}
