package org.yugzan.retry;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 * @author yongzan 2018/09/20 warp implementation {@link RestOperations} to do RetryTemplate
 */
public class RetryRestTemplate {

  private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  private RetryTemplate retryTemplate;

  private RestTemplateFactory factory;

  private boolean isDebug = false;

  private int readTimeout = DefaultRestTemplateFactory.DEFAULT_READ_TIMEOUT;

  public RetryRestTemplate() {
    this.retryTemplate = createDefaultRetryTemplate();
    this.factory = new DefaultRestTemplateFactory();
    this.factory.setReadTimeout(readTimeout);
  }

  public RetryRestTemplate(RetryTemplate retryTemplate) {
    this.retryTemplate = retryTemplate;
    this.factory = new DefaultRestTemplateFactory();
    this.factory.setReadTimeout(readTimeout);
  }

  public RetryRestTemplate(RetryTemplate retryTemplate, RestTemplateFactory factory) {
    this.retryTemplate = retryTemplate;
    this.factory = factory;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  public boolean isDebug() {
    return isDebug;
  }

  public void setDebug(boolean isDebug) {
    this.isDebug = isDebug;
  }

  @FunctionalInterface
  private interface InnerFunction<S, C, R> {
    public R apply(S string, C retryContext);
  }

  private InnerFunction<String, RetryContext, RestTemplate> rest = (key, context) -> {
    if (isDebug) {
      logger.info("Retry [{}]:{}", key, context.getRetryCount());

      System.out.println(String.format("Retry [%s]:%s", key, context.getRetryCount()));
    }
    return factory.create();
  };
  private final static String EXCUTE_INFO = "%s|%s";
  private final static String GET = "GET|%s";
  private final static String POST = "POST|%s";
  private final static String PATCH = "PATCH|%s";
  private final static String PUT = "PUT|%s";
  private final static String DELETE = "DELETE|%s";
  private final static String HEAD = "HEAD|%s";


  public RetryTemplate createDefaultRetryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
    backOffPolicy.setBackOffPeriod(5000);
    retryPolicy.setMaxAttempts(3);
    retryTemplate.setBackOffPolicy(backOffPolicy);
    retryTemplate.setRetryPolicy(retryPolicy);
    return retryTemplate;
  }

  public <T> ResponseEntity<T> get(String url, HttpEntity<?> entity, Class<T> responseType)
      throws HttpException {
    return retryTemplate.execute((context) -> rest.apply(String.format(GET, url), context)
        .exchange(url, HttpMethod.GET, entity, responseType));
  }

  public <T> T post(String url, Object entity, Class<T> responseType) throws HttpException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForObject(url, entity, responseType));
  }

  public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables)
      throws RestClientException {

    return retryTemplate.execute((context) -> rest.apply(String.format(GET, url), context)
        .getForObject(url, responseType, uriVariables));
  }


  public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(GET, url), context)
        .getForObject(url, responseType, uriVariables));
  }

  public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {

    return retryTemplate.execute(
        (context) -> rest.apply(String.format(GET, url), context).getForObject(url, responseType));
  }

  public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType,
      Object... uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(GET, url), context)
        .getForEntity(url, responseType, uriVariables));
  }

  public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType,
      Map<String, ?> uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(GET, url), context)
        .getForEntity(url, responseType, uriVariables));
  }

  public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType)
      throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(GET, url), context).getForEntity(url, responseType));
  }

  public HttpHeaders headForHeaders(String url, Object... uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(HEAD, url), context)
        .headForHeaders(url, uriVariables));
  }

  public HttpHeaders headForHeaders(String url, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(HEAD, url), context)
        .headForHeaders(url, uriVariables));
  }

  public HttpHeaders headForHeaders(URI url) throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(HEAD, url), context).headForHeaders(url));
  }


  public URI postForLocation(String url, Object request, Object... uriVariables)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForLocation(url, request, uriVariables));
  }


  public URI postForLocation(String url, Object request, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForLocation(url, request, uriVariables));
  }


  public URI postForLocation(URI url, Object request) throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(POST, url), context).postForLocation(url, request));
  }


  public <T> T postForObject(String url, Object request, Class<T> responseType,
      Object... uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForObject(url, request, responseType, uriVariables));
  }


  public <T> T postForObject(String url, Object request, Class<T> responseType,
      Map<String, ?> uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForObject(url, request, responseType, uriVariables));
  }


  public <T> T postForObject(URI url, Object request, Class<T> responseType)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForObject(url, request, responseType));
  }


  public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
      Object... uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForEntity(url, request, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
      Map<String, ?> uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForEntity(url, request, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(POST, url), context)
        .postForEntity(url, request, responseType));
  }

  public void put(String url, Object request, Object... uriVariables) throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(PUT, url), context).put(url, request, uriVariables);
      return null;
    });
  }


  public void put(String url, Object request, Map<String, ?> uriVariables)
      throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(PUT, url), context).put(url, request, uriVariables);
      return null;
    });
  }


  public void put(URI url, Object request) throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(PUT, url), context).put(url, request);
      return null;
    });
  }


  public <T> T patchForObject(String url, Object request, Class<T> responseType,
      Object... uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(PATCH, url), context)
        .patchForObject(url, request, responseType, uriVariables));
  }


  public <T> T patchForObject(String url, Object request, Class<T> responseType,
      Map<String, ?> uriVariables) throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(PATCH, url), context)
        .patchForObject(url, request, responseType, uriVariables));
  }


  public <T> T patchForObject(URI url, Object request, Class<T> responseType)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest.apply(String.format(PATCH, url), context)
        .patchForObject(url, request, responseType));
  }


  public void delete(String url, Object... uriVariables) throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(DELETE, url), context).delete(url, uriVariables);
      return null;
    });
  }


  public void delete(String url, Map<String, ?> uriVariables) throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(DELETE, url), context).delete(url, uriVariables);
      return null;
    });
  }


  public void delete(URI url) throws RestClientException {
    retryTemplate.execute((context) -> {
      rest.apply(String.format(DELETE, url), context).delete(url);
      return null;
    });
  }


  public Set<HttpMethod> optionsForAllow(String url, Object... uriVariables)
      throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(EXCUTE_INFO, "optionsForAllow", url), context)
            .optionsForAllow(url, uriVariables));
  }


  public Set<HttpMethod> optionsForAllow(String url, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(EXCUTE_INFO, "optionsForAllow", url), context)
            .optionsForAllow(url, uriVariables));
  }

  public Set<HttpMethod> optionsForAllow(URI url) throws RestClientException {
    return retryTemplate.execute((context) -> rest
        .apply(String.format(EXCUTE_INFO, "optionsForAllow", url), context).optionsForAllow(url));
  }


  public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
      Class<T> responseType, Object... uriVariables) throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
      Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity,
      Class<T> responseType) throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType));
  }


  public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
      ParameterizedTypeReference<T> responseType, Object... uriVariables)
      throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity,
      ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType, uriVariables));
  }


  public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, HttpEntity<?> requestEntity,
      ParameterizedTypeReference<T> responseType) throws RestClientException {
    return retryTemplate
        .execute((context) -> rest.apply(String.format(EXCUTE_INFO, method, url), context)
            .exchange(url, method, requestEntity, responseType));
  }


  public <T> ResponseEntity<T> exchange(RequestEntity<?> requestEntity, Class<T> responseType)
      throws RestClientException {
    return retryTemplate.execute((context) -> rest
        .apply(String.format(EXCUTE_INFO, requestEntity.getMethod(), requestEntity.getUrl()),
            context)
        .exchange(requestEntity, responseType));
  }


  public <T> ResponseEntity<T> exchange(RequestEntity<?> requestEntity,
      ParameterizedTypeReference<T> responseType) throws RestClientException {
    return retryTemplate.execute((context) -> rest
        .apply(String.format(EXCUTE_INFO, requestEntity.getMethod(), requestEntity.getUrl()),
            context)
        .exchange(requestEntity, responseType));
  }

  public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
      ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException {

    return retryTemplate.execute(
        (context) -> rest.apply(String.format(EXCUTE_INFO, method.toString(), url), context)
            .execute(url, method, requestCallback, responseExtractor, uriVariables));
  }


  public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
      ResponseExtractor<T> responseExtractor, Map<String, ?> uriVariables)
      throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(EXCUTE_INFO, method.toString(), url), context)
            .execute(url, method, requestCallback, responseExtractor, uriVariables));
  }


  public <T> T execute(URI url, HttpMethod method, RequestCallback requestCallback,
      ResponseExtractor<T> responseExtractor) throws RestClientException {
    return retryTemplate.execute(
        (context) -> rest.apply(String.format(EXCUTE_INFO, method.toString(), url), context)
            .execute(url, method, requestCallback, responseExtractor));
  }

}
