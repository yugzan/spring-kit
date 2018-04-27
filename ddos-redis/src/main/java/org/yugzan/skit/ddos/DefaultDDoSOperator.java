package org.yugzan.skit.ddos;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yongzan
 * @date 2018/04/27
 */
public class DefaultDDoSOperator implements DDoSOperator{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	private RedisTemplate<String, Long> redis;
	
	public DefaultDDoSOperator() {
		logger.info("Init DefaultDDoSOperationsFilter");
	}
	@Override
	public String generateKey(HttpServletRequest request, HttpServletResponse response) {
		String remoteAddr = request.getRemoteAddr();
		String uri = request.getRequestURI();

		if (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".ico")
				|| uri.endsWith(".png")) {
			return null;
		}
		return remoteAddr + "-" + uri;
	}

	@Override
	public void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key) {
		try {
//			response.sendRedirect("http://tw.yahoo.com");
//			response.getWriter().write("you are under attack " + expireTime);
//			response.getWriter().flush();
			String expireTime = String.valueOf( redis.getExpire(key, TimeUnit.SECONDS) );
			response.sendError(429, "Too Many Requests, Lock to "+ expireTime +" sec.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRedisTemplate(final RedisTemplate<String, Long> redis) {
		this.redis = redis;
	}

}
