package com.gpmall.pay.utils;

import com.gpmall.commons.tool.exception.BizException;
import com.gpmall.commons.tool.exception.ValidateException;
import com.gupaoedu.pay.constants.PayReturnCodeEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @description: 参数校验工具类
 * <p>校验实体中注解了javax.validation框架注解的属性</p>
 * @author shuangling.mao   1015952139
 * @date 2019-08-09 01:16
 */

public class ParamValidatorUtils {

    public static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验实体参数,返回第一条错误信息
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String validate(T t) {
        if (t == null){
            return "参数不能为空！";
        }
        Set<ConstraintViolation<T>> validationSet = validator.validate(t, Default.class);
        String message = null;
        if (validationSet != null && validationSet.size() > 0) {
            ConstraintViolation<T> violation = validationSet.iterator().next();
            message = violation.getMessage();
        }
        return message;
    }

    public static <T> void validateV2(T t) {
        String msg = validate(t);
        if (msg != null) {
            throw new ValidateException(PayReturnCodeEnum.REQUISITE_PARAMETER_NOT_EXIST.getCode(), msg);
        }
    }

    /**
     * 校验对象是否为空
     * @param para
     * @param paraName
     */
    public static void notNull(Object para, String paraName) throws BizException{
        if (para == null) {
            throw new BizException(paraName + "不能为空！");
        }
        if (para instanceof String) {
            if (((String) para).length() == 0) {
                throw new BizException(paraName + "不能为空！");
            }
        }
    }

    /**
     * 校验不能为空，如果为空，抛出biz异常
     * @param para
     * @param msg
     */
    public static void notBlank(Object para, String msg) throws BizException{
        if (para == null) {
            throw new BizException(msg);
        }
        if (para instanceof String) {
            String s = (String) para;
            if (s.isEmpty() || s.trim().isEmpty()) {
                throw new BizException(msg);
            }
        }
    }

    /**
     * 如果不为true则提示异常
     * @param expression
     * @param msg
     */
    public static void assertTrue(boolean expression, String msg) {
        if (!expression) {
            throw new BizException(msg);
        }
    }

    /**
     * 如果不为false则提示异常
     * @param expression
     * @param msg
     */
    public static void assertFalse(boolean expression, String msg) {
        if (expression) {
            throw new BizException(msg);
        }
    }
}
