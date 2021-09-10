package com.olive.filter;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
//@Component
public class MyResponseFilter extends ZuulFilter{

private static final Logger Log=LoggerFactory.getLogger(MyRequestFilter.class);
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
	    Log.info("Inside Response Filter .......");
	    RequestContext context=RequestContext.getCurrentContext();
	  
	    try(InputStream responseData=context.getResponseDataStream()) {
	    	InputStreamReader inputStreamReader = new InputStreamReader(responseData, "UTF-8");
	    	String resp = CharStreams.toString(inputStreamReader);
	    	if(resp!=null && !resp.contains("problem")) {
	    	 resp=resp+ "Modifies";
	    	 context.setResponseBody(resp);
	    	}
		} catch (Exception e) {
			Log.info("Unable To Execute {}",e.getMessage());
		}
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
