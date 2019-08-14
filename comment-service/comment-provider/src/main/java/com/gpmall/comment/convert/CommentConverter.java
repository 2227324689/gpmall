package com.gpmall.comment.convert;

import com.gpmall.comment.dal.entitys.Comment;
import com.gpmall.comment.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

/**
 * @author heps
 * @date 2019/8/14 23:54
 */
@Mapper(componentModel = "spring")
public interface CommentConverter {

    @Mappings({})
    CommentDto comment2Dto(Comment comment);
}
