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


* Use `@ComponentScan` to append the ddos filter:
(Currently, I have no idea to using other method auto import that.)
    ```java
@ComponentScan("org.yugzan.skit.ddos")
    ```

## Building

Spring Data InfluxDB uses Maven as its build system. 

Spring Data InfluxDB using Gradle.

```bash
gradle clean build
```