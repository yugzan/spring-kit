[![Release](https://jitpack.io/v/yugzan/spring-kit.svg)](https://jitpack.io/#yugzan/spring-kit)

Spring Kit - DDoS Redis 
--------------------
Build a DDoS filter using redis.


## Artifacts

### Gradle

```gradle
  allprojects {
    repositories {
      maven { url 'https://jitpack.io' }
    }
  }
  dependencies {
      compile 'com.github.yugzan.spring-kit:ddos-redis:-SNAPSHOT'
  }
```


## Usage (Spring Boot)

* Following properties can be used in your `application.yml`:

    ```yml
      spring:
        ddos.enable: true
        ddos.hit: 100
        ddos.hit.interval: 1000
        ddos.hit.blocktime: 20000
    ```
###  method 1
* Use `@EnableDefaultDDoSFilter` is a simple way to load the ddos filter by default operation:

    ```java
    
    @EnableDefaultDDoSFilter
    
    ```

###  method 2
* Or you want to customize key or response. Using `@EnableDDoSFilter` to append the ddos filter:
(do not append two annotation together, that was make confused on `DDoSfilter`.)


    ```java
    
    @EnableDDoSFilter
    
    ```

    ```java
      @Configuration
      public class MyAppConfig {
        @Bean
        public DDoSOperator myDDosOperator() {
          return new MyCustomDDoSOperator();
        }
      }
    ```
    ```java
      public class MyCustomDDoSOperator implements DDoSOperator{
        private RedisTemplate<String, Long> redis;
        
        public MyCustomDDoSOperator() {
          logger.info("Init MyCustomDDoSOperator");
        }
        @Override
        public String generateKey(HttpServletRequest request, HttpServletResponse response) {
          String remoteAddr = request.getRemoteAddr();
          String uri = request.getRequestURI();
          //do some special key rule
          return String.format("UUID_%s%s", remoteAddr , uri );
        }

        @Override
        public void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key) {
          try {
            //do some special response rule
            response.sendRedirect("http://tw.yahoo.com");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        @Override
        public void setRedisTemplate(final RedisTemplate<String, Long> redis) {
          //if need 
          this.redis = redis;
        }
      }
    ```



## Building

Spring Kit - DDoS Redis  uses Gradle as its build system. 

```bash
gradle clean build
```