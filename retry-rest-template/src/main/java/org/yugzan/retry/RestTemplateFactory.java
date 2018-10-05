package org.yugzan.retry;

import org.springframework.web.client.RestTemplate;

/**
 * @author yongzan 2018/10/05
 */
public interface RestTemplateFactory {

  public RestTemplate create();

  public void setReadTimeout(int readTimeout);
}
