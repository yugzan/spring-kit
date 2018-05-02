package org.yugzan.skit.testmodular;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author yongzan
 * @date 2018/04/27
 */
public class TestAuthenticationFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public TestAuthenticationFilter() {
		logger.info("Init {}", this.getClass().getName());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		logger.info("Do AuthenticationFilter {} URL = " + request.getRequestURL());
		chain.doFilter(request, response);
	}

}
