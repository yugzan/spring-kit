package org.yugzan.ddos;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author yongzan
 * @date 2018/04/26
 */
public class TestAuthenticationFilter extends GenericFilterBean {
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	public TestAuthenticationFilter() {

		logger.info("Init TestAuthenticationFilter.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logger.warn("Hi , this is TestAuthenticationFilter.");
		chain.doFilter(request, response);
	}

}
