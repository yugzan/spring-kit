package org.yugzan.retry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(JUnit4.class)
public class TestRetryRestTemplate {


  @Test
  public void test() {
    RetryRestTemplate rest = new RetryRestTemplate();

    rest.setDebug(true);
    ResponseEntity<String> results = rest.getForEntity("https://raw.githubusercontent.com/yugzan/spring-kit/master/README.md", String.class);
    Assert.assertEquals(HttpStatus.OK,  results.getStatusCode() );
    System.out.println( results.getBody() );

  }
}
