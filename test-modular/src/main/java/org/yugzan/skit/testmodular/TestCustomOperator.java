package org.yugzan.skit.testmodular;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.yugzan.skit.ddos.DDoSOperator;

/**
 * @author yongzan
 * @date 2018/04/30
 */
public class TestCustomOperator implements DDoSOperator {

	private String redirectUrl;

	public TestCustomOperator(String url) {
		this.redirectUrl = url;
	}

	@Override
	public void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key,
			RedisTemplate<String, Long> redis) {
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateKey(HttpServletRequest request, HttpServletResponse response,
			RedisTemplate<String, Long> redis) {
		String remoteAddr = request.getRemoteAddr();
		String contentType = request.getContentType();
		String uri = request.getRequestURI();
		
		if (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".ico")
				|| uri.endsWith(".png") || contentType == null) {
			return null;
		}
		return remoteAddr + ">" + uri;
	}

}
