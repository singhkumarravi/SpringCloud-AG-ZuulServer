package com.olive.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
//@Component
public class MyErrorFilter extends ZuulFilter{
private static final Logger log=LoggerFactory.getLogger(MyErrorFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
	      log.info("My Own Custom ERROR FILTER...");
	      try {
	      RequestContext context=RequestContext.getCurrentContext();
	      Throwable th = context.getThrowable();
	      if(th!=null && th instanceof ZuulException)
	      {
	        ZuulException zull=(ZuulException)th;  
	      log.info("Zull exception Fail " + th.getMessage());
	      context.setResponseBody("{\"code\":500,\"problem\":\"NO Service Found\"}"); 
	      context.getResponse().setContentType("application/json");
	      context.setResponseStatusCode(500);
	      
	      //do not execute the SendErrorFilter
	      context.remove("throwable");  
	      }
	      } catch (Exception e) {
	    	  log.error("EXCEPTION WHITE CUSTOM FILTER EXECUTION");
				throw e; //send to SendErrorFilter
			}
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return -1;
	}
	

}
