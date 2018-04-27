package org.yugzan.skit.ddos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yongzan
 * @date 2018/04/27
 */

public interface DDoSOperator {

    /**
     * if want using RedisTemplate
     * 
     * @param redis
     * @return 
     */
	void setRedisTemplate(final RedisTemplate<String, Long> redis);

    /**
     * if the condition is ignore, then return null
     * 
     * @param request
     * @param response
     * @return String of key
     */
	String generateKey(HttpServletRequest request, HttpServletResponse response);

    /**
     * response to user.
     * 
     * @param request
     * @param response
     * @param key
     * @return
     */
	void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key);
}
