package com.gpmall.comment.bootstrap;

import com.gpmall.comment.dal.entitys.Comment;
import com.gpmall.comment.dal.entitys.CommentPicture;
import com.gpmall.comment.dal.entitys.CommentReply;
import com.gpmall.comment.dal.persistence.CommentMapper;
import com.gpmall.comment.dal.persistence.CommentPictureMapper;
import com.gpmall.comment.dal.persistence.CommentReplyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentProviderApplicationTest {
    @Test
    public void contextLoads() {
    }

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentPictureMapper commentPictureMapper;
    @Autowired
    CommentReplyMapper commentReplyMapper;
    @Test
    public void  test1(){
        Comment comment = commentMapper.selectByPrimaryKey(1L);
        CommentPicture commentPicture = commentPictureMapper.selectByPrimaryKey(1L);
        CommentReply commentReply = commentReplyMapper.selectByPrimaryKey(1L);
        System.out.println("coupon = " + comment);
        System.out.println("couponCode = " + commentPicture);
        System.out.println("couponCode = " + commentReply);
    }
}