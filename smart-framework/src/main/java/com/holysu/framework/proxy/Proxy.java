package com.holysu.framework.proxy;

/**
 * 代理接口
 *
 * @author minorpoet 2018/7/6
 */
public interface Proxy {
    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
