package com.gpmall.commons.result;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public abstract class AbstractRequest implements Serializable{

    private static final long serialVersionUID = 1717442845820713651L;
    public abstract void requestCheck();

    @Override
    public String toString() {
        return "AbstractRequest{}";
    }
}
