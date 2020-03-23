package com.gpmall.comment.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gpmall.comment.CommentException;
import com.gpmall.comment.ICommentService;
import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.comment.convert.CommentConverter;
import com.gpmall.comment.dal.entitys.Comment;
import com.gpmall.comment.dal.entitys.CommentPicture;
import com.gpmall.comment.dal.persistence.CommentMapper;
import com.gpmall.comment.dal.persistence.CommentPictureMapper;
import com.gpmall.comment.dto.*;
import com.gpmall.comment.utils.ExceptionProcessorUtil;
import com.gpmall.comment.utils.GlobalIdGeneratorUtil;
import com.gpmall.comment.utils.SensitiveWordsUtil;
import com.gpmall.order.OrderQueryService;
import com.gpmall.order.dto.OrderDto;
import com.gpmall.order.dto.OrderItemRequest;
import com.gpmall.order.dto.OrderItemResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author heps
 * @date 2019/8/12 21:13
 * 商品评价服务实现
 */
@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentMapper commentMapper;

    private final CommentPictureMapper commentPictureMapper;

    private final CommentConverter commentConverter;

    @Reference
    private OrderQueryService orderQueryService;

    private final GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private static final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

    private static final String COMMENT_PICTURE_GLOBAL_ID_CACHE_KEY = "COMMENT_PICTURE_ID";

    public CommentServiceImpl(CommentMapper commentMapper, CommentPictureMapper commentPictureMapper,
                              CommentConverter commentConverter, GlobalIdGeneratorUtil globalIdGeneratorUtil) {
        this.commentMapper = commentMapper;
        this.commentPictureMapper = commentPictureMapper;
        this.commentConverter = commentConverter;
        this.globalIdGeneratorUtil = globalIdGeneratorUtil;
    }

    @Override
    public AddCommentResponse addComment(AddCommentRequest request) {
        AddCommentResponse response = new AddCommentResponse();
        response.setCode(CommentRetCode.SUCCESS.getCode());
        response.setMsg(CommentRetCode.SUCCESS.getMessage());
        try {
            request.requestCheck();
            checkSensitiveWords(request.getContent());
            return doAddComment(request);
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public CommentResponse comment(CommentRequest request) {
        CommentResponse response = new CommentResponse();
        try {
            request.requestCheck();

            String orderItemId = request.getOrderItemId();
            OrderItemRequest orderItemRequest = new OrderItemRequest();
            orderItemRequest.setOrderItemId(orderItemId);
            OrderItemResponse orderItemResponse = orderQueryService.orderItem(orderItemRequest);

            String itemId = orderItemResponse.getItemId();
            String orderId = orderItemResponse.getOrderId();

            Example example = new Example(Comment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            criteria.andEqualTo("orderId", orderId);
            criteria.andEqualTo("isDeleted", false);
            List<Comment> comments = commentMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(comments)) {
                response.setCode(CommentRetCode.COMMENT_NOT_EXIST.getCode());
                response.setMsg(CommentRetCode.COMMENT_NOT_EXIST.getMessage());
            } else {
                response.setCode(CommentRetCode.SUCCESS.getCode());
                response.setMsg(CommentRetCode.SUCCESS.getMessage());
                response.setCommentDtoList(commentConverter.comment2Dto(comments));
            }
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public CommentListResponse commentList(CommentListRequest request) {
        CommentListResponse response = new CommentListResponse();
        try {
            request.requestCheck();
            String itemId = request.getItemId();
            Integer type = request.getType();
            Example example = new Example(Comment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            if (type != null) {
                criteria.andEqualTo("type", type.byteValue());
            }
            criteria.andEqualTo("isDeleted", false);
            PageHelper.startPage(request.getPage(), request.getSize());
            List<Comment> comments = commentMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(comments)) {
                response.setCode(CommentRetCode.COMMENT_NOT_EXIST.getCode());
                response.setMsg(CommentRetCode.COMMENT_NOT_EXIST.getMessage());
            } else {
                response.setCode(CommentRetCode.SUCCESS.getCode());
                response.setMsg(CommentRetCode.SUCCESS.getMessage());
                response.setPage(request.getPage());
                response.setSize(request.getSize());
                PageInfo<Comment> commentPageInfo = new PageInfo<>();
                response.setTotal(commentPageInfo.getTotal());
                response.setCommentDtoList(commentConverter.comment2Dto(comments));
            }
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public TotalCommentResponse totalComment(TotalCommentRequest request) {
        TotalCommentResponse response = new TotalCommentResponse();
        try {
            request.requestCheck();
            String itemId = request.getItemId();
            Integer type = request.getType();
            Example example = new Example(Comment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            if (type != null) {
                criteria.andEqualTo("type", type.byteValue());
            }
            int count = commentMapper.selectCountByExample(example);
            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
            response.setTotal(count);
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public DeleteCommentResponse deleteComment(DeleteCommentRequest request) {
        DeleteCommentResponse response = new DeleteCommentResponse();
        try {
            request.requestCheck();
            String commentId = request.getCommentId();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            if (comment == null || !comment.getIsDeleted()) {
                throw new CommentException(CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getCode(), CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getMessage());
            }
            comment.setDeletionUserId(request.getUserId());
            comment.setIsDeleted(true);
            comment.setDeletionTime(new Date());

            Example example = new Example(CommentPicture.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("commentId", commentId);
            commentPictureMapper.deleteByExample(example);

            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public TopCommentResponse topComment(TopCommentRequest request) {
        TopCommentResponse response = new TopCommentResponse();
        try {
            request.requestCheck();

            String commentId = request.getCommentId();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            if (comment == null || !comment.getIsDeleted()) {
                throw new CommentException(CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getCode(), CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getMessage());
            }
            comment.setIsTop(true);
            commentMapper.updateByPrimaryKey(comment);
            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return null;
    }

    @Override
    public AuditCommentResponse auditComment(AuditCommentRequest request) {
        AuditCommentResponse response = new AuditCommentResponse();
        try {
            request.requestCheck();
            String commentId = request.getCommentId();
            Comment comment = commentMapper.selectByPrimaryKey(commentId);
            if (comment == null || !comment.getIsDeleted()) {
                throw new CommentException(CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getCode(), CommentRetCode.CURRENT_COMMENT_NOT_EXIST.getMessage());
            }
            comment.setIsValid(request.isValid());
            comment.setValidationUserId(request.getValidationUserId());
            comment.setValidationTime(new Date());
            comment.setValidationSuggestion(request.getValidationSuggestion());
            commentMapper.updateByPrimaryKey(comment);

            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    @Override
    public ItemScoreResponse itemScore(ItemScoreRequest request) {
        ItemScoreResponse response = new ItemScoreResponse();
        try {
            request.requestCheck();
            String itemId = request.getItemId();
            Example example = new Example(Comment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            criteria.andEqualTo("type", 1);
            int goodCommentCount = commentMapper.selectCountByExample(example);

            example = new Example(Comment.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            criteria.andEqualTo("type", 3);
            int badCommentCount = commentMapper.selectCountByExample(example);
            if (badCommentCount == 0) {
                response.setScore(100);
            } else {
                double score = goodCommentCount / (goodCommentCount + badCommentCount);
                response.setScore(score);
            }
            response.setCode(CommentRetCode.SUCCESS.getCode());
            response.setMsg(CommentRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            ExceptionProcessorUtil.handleException(response, e);
        }
        return response;
    }

    /**
     * 执行业务逻辑
     * @param request 评价参数
     */
    private AddCommentResponse doAddComment(AddCommentRequest request) {
        AddCommentResponse response = new AddCommentResponse();
        String orderItemId = request.getOrderItemId();

        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setOrderItemId(orderItemId);
        OrderItemResponse orderItemResponse = orderQueryService.orderItem(orderItemRequest);
        OrderDto orderDto = orderItemResponse.getOrderDto();
        String orderId = orderItemResponse.getOrderId();
        String itemId = orderItemResponse.getItemId();

        Example example = new Example(Comment.class);
        example.createCriteria()
        .andEqualTo(orderId)
        .andEqualTo(itemId);
        List<Comment> comments = commentMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(comments)) {
            throw new CommentException(CommentRetCode.CURRENT_ORDER_ITEM_EXISTS_COMMENT.getCode(), CommentRetCode.CURRENT_ORDER_ITEM_EXISTS_COMMENT.getMessage());
        }

        Comment comment = new Comment();
        comment.setId(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
        comment.setOrderId(orderId);
        comment.setItemId(itemId);
        if (request.getStar() == null) {
            comment.setStar((byte) 5);
        } else {
            comment.setStar(request.getStar().byteValue());
        }
        if (request.getType() == null) {
            comment.setType((byte) 1);
        } else {
            comment.setType(request.getType().byteValue());
        }
        if (request.getIsAnoymous() == null) {
            comment.setIsAnoymous(true);
        } else {
            comment.setIsAnoymous(request.getIsAnoymous());
        }
        comment.setContent(request.getContent());
        comment.setBuyerNick(orderDto.getBuyerNick());
        comment.setCommentTime(new Date());
        comment.setIsPublic(request.getIsPublic());
        comment.setIsValid(false);
        comment.setIsTop(false);
        comment.setIsDeleted(false);
        comment.setUserId(orderDto.getUserId());
        commentMapper.insert(comment);

        if (CollectionUtils.isEmpty(request.getPicPaths())) {
            saveCommentPictures(comment.getId(), request.getPicPaths());
        }
        response.setCode(CommentRetCode.SUCCESS.getCode());
        response.setMsg(CommentRetCode.SUCCESS.getMessage());
        return response;
    }

    /**
     * 保存评价图片
     * @param commentId 商品评价id
     * @param picPaths 图片路径
     */
    private void saveCommentPictures(String commentId, List<String> picPaths) {
        CommentPicture commentPicture;
        for (String picPath : picPaths) {
            commentPicture = new CommentPicture();
            commentPicture.setCommentId(commentId);
            commentPicture.setPicPath(picPath);
            commentPicture.setId(globalIdGeneratorUtil.getNextSeq(COMMENT_PICTURE_GLOBAL_ID_CACHE_KEY, 1));
            commentPictureMapper.insert(commentPicture);
        }
    }

    private void checkSensitiveWords(String content) {
        boolean exist = SensitiveWordsUtil.existSensitiveWords(content);
        if (exist) {
            throw new CommentException(CommentRetCode.EXIST_SENSITIVE_WORDS.getCode(), CommentRetCode.EXIST_SENSITIVE_WORDS.getMessage());
        }
    }
}
