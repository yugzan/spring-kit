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
        ddos.interval: 1000
        ddos.blocktime: 60000
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

        @Override
        public String generateKey(HttpServletRequest request, HttpServletResponse response, RedisTemplate<String, Long> redis) {
          String remoteAddr = request.getRemoteAddr();
          String uri = request.getRequestURI();
          //do some special key rule
          return String.format("UUID_%s%s", remoteAddr , uri );
        }

        @Override
        public void notifyAttack(HttpServletRequest request, HttpServletResponse response, String key, RedisTemplate<String, Long> redis) {
          try {
            //do some special response rule
            response.sendRedirect("http://tw.yahoo.com");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
    ```

## Spring Security

Here is a SpringSecurity example [ [full](https://github.com/yugzan/spring-kit/tree/master/test-modular) ]

just add `@EnableDDoSFilter` annotation then security config add filter

    ```java
    @Configuration
    public static  class FromSecurityConfiguration extends WebSecurityConfigurerAdapter{
      
      @Autowired
      private DDoSfilter ddoSfilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .csrf().disable()
        .addFilterBefore( ddoSfilter , UsernamePasswordAuthenticationFilter.class)
        .formLogin().and()
        .httpBasic();
    }
    }

  @Bean
  public DDoSOperator getoperator() {
    return new DefaultDDoSOperator();
  }
    ```


## Building

Spring Kit - DDoS Redis  uses Gradle as its build system. 

```bash
  gradle clean build
```

### Vaildate

we can using client-side tool [ [hey](https://github.com/rakyll/hey) ] try request and statistics response.

#### Install 

```bash
  go get -u github.com/rakyll/hey
```
or  just build 
```bash
  $ git clone https://github.com/rakyll/hey.git
  $ cd hey
  $ go build
```

#### Usage

run 200 request and client 1

```bash
  $ ./hey -n 200 -c 1 http://127.0.0.1:8080/api
```

Result:

```bash
Summary:
  Total:  0.7534 secs
  Slowest:  0.2426 secs
  Fastest:  0.0014 secs
  Average:  0.0038 secs
  Requests/sec: 265.4796
  

Response time histogram:
  0.001 [1] |
  0.026 [198] |■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.050 [0] |
  0.074 [0] |
  0.098 [0] |
  0.122 [0] |
  0.146 [0] |
  0.170 [0] |
  0.194 [0] |
  0.218 [0] |
  0.243 [1] |


Latency distribution:
  10% in 0.0015 secs
  25% in 0.0017 secs
  50% in 0.0024 secs
  75% in 0.0030 secs
  90% in 0.0034 secs
  95% in 0.0040 secs
  99% in 0.0196 secs

Details (average, fastest, slowest):
  DNS+dialup: 0.0000 secs, 0.0014 secs, 0.2426 secs
  DNS-lookup: 0.0000 secs, 0.0000 secs, 0.0000 secs
  req write:  0.0000 secs, 0.0000 secs, 0.0001 secs
  resp wait:  0.0035 secs, 0.0012 secs, 0.2405 secs
  resp read:  0.0002 secs, 0.0001 secs, 0.0017 secs

Status code distribution:
  [401] 100 responses
  [429] 100 responses


```