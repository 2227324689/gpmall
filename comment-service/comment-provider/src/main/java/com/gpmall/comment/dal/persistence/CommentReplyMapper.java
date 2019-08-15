package com.gpmall.comment.dal.persistence;

import com.gpmall.comment.dal.entitys.CommentReply;
import com.gpmall.comment.dal.entitys.CommentReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentReplyMapper {
    long countByExample(CommentReplyExample example);

    int deleteByExample(CommentReplyExample example);

    int deleteByPrimaryKey(String id);

    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    List<CommentReply> selectByExample(CommentReplyExample example);

    CommentReply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByExample(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByPrimaryKeySelective(CommentReply record);

    int updateByPrimaryKey(CommentReply record);
}