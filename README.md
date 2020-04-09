# SpringFramework
## 静态工厂和实例工厂 ##
当使用这种用bulider构建的对象的时候，需要特殊处理
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
```
**静态工厂**
```java
public class OkHttpUtils {
    private static OkHttpClient OkHttpClient;
    public static OkHttpClient getInstance() {
        if (OkHttpClient == null) {
            OkHttpClient = new OkHttpClient.Builder().build();
        }
        return OkHttpClient;
    }
}
```
```xml
<bean class="org.javaboy.OkHttpUtils" factory-method="getInstance" id="okHttpClient"></bean>
```
**实例工厂**
```java
public class OkHttpUtils {
    private OkHttpClient OkHttpClient;
    public OkHttpClient getInstance() {
        if (OkHttpClient == null) {
            OkHttpClient = new OkHttpClient.Builder().build();
        }
        return OkHttpClient;
    }
}
```
```xml
<bean class="org.javaboy.OkHttpUtils" id="okHttpUtils"/>
<bean class="okhttp3.OkHttpClient" factory-bean="okHttpUtils" factory-method="getInstance" id="okHttpClient"></bean>
```
