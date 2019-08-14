package com.gpmall.comment.dal.persistence;

import com.gpmall.comment.dal.entitys.CommentPicture;
import com.gpmall.comment.dal.entitys.CommentPictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentPictureMapper {
    long countByExample(CommentPictureExample example);

    int deleteByExample(CommentPictureExample example);

    int deleteByPrimaryKey(String id);

    int insert(CommentPicture record);

    int insertSelective(CommentPicture record);

    List<CommentPicture> selectByExample(CommentPictureExample example);

    CommentPicture selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CommentPicture record, @Param("example") CommentPictureExample example);

    int updateByExample(@Param("record") CommentPicture record, @Param("example") CommentPictureExample example);

    int updateByPrimaryKeySelective(CommentPicture record);

    int updateByPrimaryKey(CommentPicture record);
}