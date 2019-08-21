package com.gpmall.comment;

import com.gpmall.comment.dto.AddCommentReplyRequest;
import com.gpmall.comment.dto.AddCommentReplyResponse;

/**
 * @author hepengshuai
 * @date 2019/8/21 20:46
 * 商品评价回复服务接口
 */
public interface ICommentReplyService {

    /**
     * 新增商品评价回复
     * @param request
     * @return
     */
    AddCommentReplyResponse addCommentReply(AddCommentReplyRequest request);
}
