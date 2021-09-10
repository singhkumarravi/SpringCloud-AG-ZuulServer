package com.olive.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
@Component
public class MyRouteFilter extends ZuulFilter{

	private static final Logger log=LoggerFactory.getLogger(MyRouteFilter.class);
	
	@Value("${error.path:/error}")
	private String errorPath;
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
	     log.info("FROM ROUTE FILTER...");
	     RequestContext context=RequestContext.getCurrentContext();
	     //read the current request object
	     HttpServletRequest request = context.getRequest();
	     //read header
	     String auth  = request.getHeader("Authorization");
	     
	     if(!StringUtils.hasText(auth))
	     {
	       log.info("Send To Login Service !");	 
	    //	 RequestDispatcher rd = request.getRequestDispatcher("/user-api/user/login");
	       request.setAttribute("javax.servlet.error.status_code",500);
	       request.setAttribute("javax.servlet.error.message","Unable to Find Authorization");
	       request.setAttribute("message","Unable to Find Authorization");
	       RequestDispatcher dispatcher =request.getRequestDispatcher(this.errorPath);
	       try {
	       dispatcher.forward(request,context.getResponse());
	       } catch (ServletException e) {
	       e.printStackTrace();
	       } catch (IOException e) {
	       e.printStackTrace();
	       }
	     }
	     else
	     {
	    	 log.info("Good ! authorization detail found !! ");
	    	 
	     }
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return -1;
	}
	

}
