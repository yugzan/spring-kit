package org.yugzan.skit.ddos;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yongzan
 * @date 2018/05/02
 */
@ConfigurationProperties("spring.ddos")
public class DDoSProperties {

	// MAX_HIT_COUNT_PER_IP
	private long hit;
	// HIT_TIME_INTERVAL
	private long interval;
	// BLOCK_TIME
	private long blocktime;

	public long getHit() {
		return hit;
	}

	public long getBlocktime() {
		return blocktime;
	}

	public void setHit(long hit) {
		this.hit = hit;
	}

	public void setBlocktime(long blocktime) {
		this.blocktime = blocktime;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}
