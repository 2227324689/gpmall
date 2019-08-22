package com.gpmall.comment.service;

import com.gpmall.comment.CommentException;
import com.gpmall.comment.ICommentReplyService;
import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.comment.dal.entitys.Comment;
import com.gpmall.comment.dal.entitys.CommentReply;
import com.gpmall.comment.dal.persistence.CommentMapper;
import com.gpmall.comment.dal.persistence.CommentReplyMapper;
import com.gpmall.comment.dto.AddCommentReplyRequest;
import com.gpmall.comment.dto.AddCommentReplyResponse;
import com.gpmall.comment.utils.ExceptionProcessorUtil;
import com.gpmall.comment.utils.GlobalIdGeneratorUtil;
import org.apache.dubbo.config.annotation.Service;

import java.util.Date;

/**
 * @author heps
 * @date 2019/8/21 22:55
 * 商品评价回复服务实现
 */
@Service
public class CommentReplyServiceImpl implements ICommentReplyService {

    private final CommentReplyMapper commentReplyMapper;

    private final CommentMapper commentMapper;

    private final GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private static final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_REPLY_ID";

    public CommentReplyServiceImpl(CommentReplyMapper commentReplyMapper, CommentMapper commentMapper, GlobalIdGeneratorUtil globalIdGeneratorUtil) {
        this.commentReplyMapper = commentReplyMapper;
        this.commentMapper = commentMapper;
        this.globalIdGeneratorUtil = globalIdGeneratorUtil;
    }

    @Override
    public AddCommentReplyResponse addCommentReply(AddCommentReplyRequest request) {
        AddCommentReplyResponse response = new AddCommentReplyResponse();
        try {
            request.requestCheck();
            CommentReply commentReply = new CommentReply();
            String commentId = request.getCommentId();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            if (comment != null && (comment.getIsDeleted() == null || !comment.getIsDeleted())) {
                commentReply.setCommentId(commentId);
            } else {
                CommentReply reply = commentReplyMapper.selectByPrimaryKey(commentId);
                if (reply != null) {
                    commentReply.setParentId(commentId);
                } else {
                    throw new CommentException(CommentRetCode.ORIGIN_COMMENT_NOT_EXIST.getCode(), CommentRetCode.ORIGIN_COMMENT_NOT_EXIST.getMessage());
                }
            }
            commentReply.setId(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
            commentReply.setContent(request.getContent());
            commentReply.setReplyTime(new Date());
            commentReply.setReplyNick(request.getReplyNick());
            commentReply.setUserId(request.getUserId());
            commentReply.setIsDeleted(false);
            commentReplyMapper.insert(commentReply);

            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }
}
