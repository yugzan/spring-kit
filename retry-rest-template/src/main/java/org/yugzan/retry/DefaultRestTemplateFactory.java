package org.yugzan.retry;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author yongzan 
 * 2018/08/29
 */

public class DefaultRestTemplateFactory implements RestTemplateFactory {

  public static final int DEFAULT_READ_TIMEOUT = 60000; // millisecond

  protected int readTimeout = DEFAULT_READ_TIMEOUT;

  @Override
  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  @Override
  public RestTemplate create() {
    try {
      return new RestTemplate(httpRequestFactory(this.readTimeout));
    } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected HttpComponentsClientHttpRequestFactory httpRequestFactory(int readTimeout)
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    // use the TrustSelfSignedStrategy to allow Self Signed Certificates
    SSLContext sslContext =
        SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
        // disable hostname verification.
        .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    HttpComponentsClientHttpRequestFactory httpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(httpClient);
    httpRequestFactory.setReadTimeout(readTimeout);
    return httpRequestFactory;
  }


}
