package com.lx.service.stock.impl;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/11/18
 */

@Service
@Slf4j
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {
    @Override
    public String httpGet(String url) {
        log.info("httpGet-start.....URL={}", url);
        String res = HttpUtils.get(url);
        log.info("httpGet-end.....URL={}", url);
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        log.info("httpPost-start.....URL={},PARAMS={}", url, params);
        String res = HttpUtils.post(url, params);
        log.info("httpPost-end.....URL={},PARAMS={}", url, params);
        return res;
    }
}
