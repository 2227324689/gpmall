package com.gpmall.comment;

import com.gpmall.comment.dto.*;

/**
 * @author heps
 * @date 2019/8/11 22:57
 *
 * 商品评价服务接口
 */
public interface ICommentService {

    /**
     * 添加商品评价
     * @param request 请求参数
     * @return 评价结果
     */
    AddCommentResponse addComment(AddCommentRequest request);

    /**
     * 根据订单详情id查询评价
     * @param request
     * @return
     */
    CommentResponse comment(CommentRequest request);

    /**
     * 分页查询某个商品的评价
     * @param request
     * @return
     */
    CommentListResponse commentList(CommentListRequest request);

    /**
     * 查询某个商品的评价总数
     * @param request
     * @return
     */
    TotalCommentResponse totalComment(TotalCommentRequest request);

    /**
     * 删除评价
     * @param request
     * @return
     */
    DeleteCommentResponse deleteComment(DeleteCommentRequest request);

    /**
     * 将商品评价置顶
     * @param request
     * @return
     */
    TopCommentResponse topComment(TopCommentRequest request);

    /**
     * 商品评价审核
     * @param request
     * @return
     */
    AuditCommentResponse auditComment(AuditCommentRequest request);

    /**
     * 根据商品评价计算综合评分
     * @param request
     * @return
     */
    ItemScoreResponse itemScore(ItemScoreRequest request);
}
