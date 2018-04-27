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


* Use `@EnableDDoSFilter` to append the ddos filter:

    ```java
    
    @EnableDDoSFilter
    
    ```

## Building

Spring Kit - DDoS Redis  uses Gradle as its build system. 

```bash
gradle clean build
```