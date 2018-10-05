[![Release](https://jitpack.io/v/yugzan/spring-kit.svg)](https://jitpack.io/#yugzan/spring-kit)

Spring Kit - RetryRest Template 1.0.2
--------------------
Build a RetryTemplate with RestTemplate


## Artifacts

### Gradle

```gradle
  allprojects {
    repositories {
      maven { url 'https://jitpack.io' }
    }
  }
  dependencies {
        compile 'com.github.yugzan.spring-kit:retry-rest-template:1.0.2'
  }
```


## Usage (Spring Boot)


###  Configuration

* In Spring you can use `@Bean` to generate an bean :

    ```java
    
  @Bean
  public RetryRestTemplate retryRestTemplate() {
    RetryRestTemplate template = new RetryRestTemplate();
    template.setReadTimeout(READ_TIMEOUT);
    template.setDebug(true);
    return template;
  }
    
    ```
* Then autowired to use just like RestTemplate.

    ```java
    
  @Autowired
  private RetryRestTemplate retry;
    
    ```

    ```java
      public class someAPIRequestMethod {
          retry.post(url + "/something", entity, ResponseItem.class);
      }
    ```
    
* If you want to configuration, here has some kind for constructor 
* RetryTemplate : configurate your retry condition.
* RestTemplateFactory : configurate your RestTemplate generate or build own thread pool

    ```java
public interface RestTemplateFactory {

  public RestTemplate create();

  public void setReadTimeout(int readTimeout);
}
    ```
