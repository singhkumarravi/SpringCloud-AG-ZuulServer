package com.olive.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
//@Component
public class MyRequestFilter extends ZuulFilter{
private static final Logger Log=LoggerFactory.getLogger(MyRequestFilter.class);
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
       Log.info("Inside Request Filter ........");
       RequestContext context =RequestContext.getCurrentContext();
       HttpServletRequest request = context.getRequest();
       Log.info(" URL From Req => {} ", request.getRequestURL());
       Log.info(" Media Type From Req => {} ", request.getContentType());
       Log.info(" Method Type From Req => {} ",request.getMethod() );
       Log.info(" Length From Req => {} ",request.getContentLength());
       
       return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
