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
     * if the condition is ignore, then return null
     * 
     * @param request
     * @param response
     * @return String of key
     */
	String generateKey(HttpServletRequest request, HttpServletResponse response, RedisTemplate<String, Long> redis);

    /**
     * response to user.
     * 
     * @param request
     * @param response
     * @param redis key
     */
	void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key, RedisTemplate<String, Long> redis);
}
