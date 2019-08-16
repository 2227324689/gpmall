package com.gpmall.comment.service;

import com.gpmall.comment.CommentException;
import com.gpmall.comment.ICommentService;
import com.gpmall.comment.constant.CommentRetCode;
import com.gpmall.comment.dal.entitys.Comment;
import com.gpmall.comment.dal.entitys.CommentExample;
import com.gpmall.comment.dal.entitys.CommentPicture;
import com.gpmall.comment.dal.persistence.CommentMapper;
import com.gpmall.comment.dal.persistence.CommentPictureMapper;
import com.gpmall.comment.dto.AddCommentRequest;
import com.gpmall.comment.dto.AddCommentResponse;
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

import java.util.Date;
import java.util.List;

/**
 * @author heps
 * @date 2019/8/12 21:13
 * 商品评价服务实现
 */
@Service
public class CommentServiceImpl implements ICommentService {

    private CommentMapper commentMapper;

    private CommentPictureMapper commentPictureMapper;

    @Reference
    private OrderQueryService orderQueryService;

    private GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

    private final String COMMENT_PICTURE_GLOBAL_ID_CACHE_KEY = "COMMENT_PICTURE_ID";

    public CommentServiceImpl(CommentMapper commentMapper, CommentPictureMapper commentPictureMapper, GlobalIdGeneratorUtil globalIdGeneratorUtil) {
        this.commentMapper = commentMapper;
        this.commentPictureMapper = commentPictureMapper;
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

        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andItemIdEqualTo(itemId);
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
            comment.setStar((byte) request.getStar().intValue());
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
