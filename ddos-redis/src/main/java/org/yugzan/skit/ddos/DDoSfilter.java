package org.yugzan.skit.ddos;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author yongzan
 * @date 2016/7/5
 */
@Component
@ConditionalOnExpression("${spring.ddos.enable:true}")
public class DDoSfilter extends OncePerRequestFilter{ 

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${spring.ddos.hit:10}")
    private long MAX_HIT_COUNT_PER_IP;
    
    @Value("${spring.ddos.hit.interval:300}")
    private long HIT_TIME_INTERVAL;

    @Value("${spring.ddos.hit.blocktime:60000}")
    private long BLOCK_TIME;

    @Autowired
    @Qualifier("ddosRedisTemplate")
    private RedisTemplate<String, Long>  redis;
    
    private  DDoSOperator operator;
    
    public DDoSfilter(
    		@Autowired  DDoSOperator operator, 
    	    @Autowired
    		@Qualifier("ddosRedisTemplate")  RedisTemplate<String, Long>  redis) {
    	this.operator = operator;
    	this.operator.setRedisTemplate(redis);
    	logger.info("Init DDoSfilter");
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String key = operator.generateKey(request , response);

        if (key != null) {
            if ( Objects.nonNull( redis.opsForValue().get(key) ) ) {
            	redis.opsForValue().increment(key, 1);
            	Long hitCount = redis.opsForValue().get(key);
            	if (hitCount >= MAX_HIT_COUNT_PER_IP) {
                	if(hitCount == MAX_HIT_COUNT_PER_IP) {
            			redis.expire(key, BLOCK_TIME, TimeUnit.MILLISECONDS);
                	}

                	operator.notifyAttack(request, response, key);
                    logger.warn("suspicious access for {}:hit:[{}]", key, hitCount);
                    return;
                } else {
        			redis.expire(key, HIT_TIME_INTERVAL, TimeUnit.MILLISECONDS);
                    logger.info("{}, hit:[{}]", key, hitCount);
                }
            } else {
            	redis.opsForValue().increment(key, 0);
    			redis.expire(key, HIT_TIME_INTERVAL, TimeUnit.MILLISECONDS);
                logger.info("new statistics for {}", key);
            }
        	
        }else {
//            logger.debug("not monitor address {}", remoteAddr);
        }
        filterChain.doFilter(request, response);
    }
}
