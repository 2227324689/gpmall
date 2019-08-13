package com.gpmall.comment;

import com.gpmall.comment.dto.AddCommentRequest;
import com.gpmall.comment.dto.AddCommentResponse;

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
}
