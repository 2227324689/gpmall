package com.gpmall.pay.biz.abs;

import java.util.SortedMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * @author 风骚的Michael 老师
 */
public class Context {

    public Context() {
        super();
    }

    SortedMap<String, Object> sParaTemp;

    public SortedMap<String, Object> getsParaTemp() {
        return sParaTemp;
    }

    public void setsParaTemp(SortedMap<String, Object> sParaTemp) {
        this.sParaTemp = sParaTemp;
    }
}
