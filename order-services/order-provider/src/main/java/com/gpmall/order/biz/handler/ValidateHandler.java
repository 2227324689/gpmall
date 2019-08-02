package com.gpmall.order.biz.handler;/**
 * Created by mic on 2019/8/1.
 */

import com.gpmall.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午4:47
 *
 * TODO:  如何解决商品的超卖问题？ 我这里没有进行扩展，有兴趣的同学可以进行扩展
 */
@Slf4j
@Component
public class ValidateHandler extends AbstractTransHandler {
    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        return false;
    }

    /*@Autowired
    OrderMapper orderMapper;

    @Reference
    IMemberService memberService;

    @Override
    public void handler(OrderContext context, HandlerChain<OrderContext, BizException> handlers) throws BizException {
        log.info("begin -OrderValidateHandler:request:"+context);
        QueryMemberRequest queryMemberRequest =new QueryMemberRequest();
        queryMemberRequest.setUserId(context.getRequest().getUserId());
        QueryMemberResponse response=memberService.queryMemberById(queryMemberRequest);
        if(OrderRetCode.SUCCESS.getCode().equals(response.getCode())){
            context.setMemberResponse(response);
            handlers.handleNext(context); //调用下一个链进行处理
        }else{
            throw new BizException(response.getCode(),response.getMsg());
        }

    }*/
}
