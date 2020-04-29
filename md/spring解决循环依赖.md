https://juejin.im/post/5e927e27f265da47c8012ed9  

**先说明前提：原型(Prototype)的场景是不支持循环依赖的，通常会走到AbstractBeanFactory类中下面的判断，抛出异常。**
```java
if (isPrototypeCurrentlyInCreation(beanName)) {
  throw new BeanCurrentlyInCreationException(beanName);
}
```
原因很好理解，创建新的A时，发现要注入原型字段B，又创建新的B发现要注入原型字段A...  
这就套娃了, 你猜是先StackOverflow还是OutOfMemory？  
Spring怕你不好猜，就先抛出了BeanCurrentlyInCreationException

## Spring 解决循环依赖
spring内部维护了3个Map，也就是三级缓存。  
在Spring的DefaultSingletonBeanRegistry类中，你会赫然发现类上方挂着这三个Map：  
- **singletonObjects** 它是我们最熟悉的朋友，俗称“单例池”“容器”，缓存创建完成单例Bean的地方。
- **singletonFactories** 映射创建Bean的原始工厂
- **earlySingletonObjects** 映射Bean的早期引用，也就是说在这个Map里的Bean不是完整的，甚至还不能称之为“Bean”，只是一个Instance.

后两个Map其实是“垫脚石”级别的，只是创建Bean的时候，用来借助了一下，创建完成就清掉了。  

为什么成为后两个Map为垫脚石，假设最终放在singletonObjects的Bean是你想要的一杯“凉白开”。
那么Spring准备了两个杯子，即**singletonFactories**和**earlySingletonObjects**来回“倒腾”几番，把热水晾成“凉白开”放到**singletonObjects**中。  

## 如何自己实现

```java
public class A { 
    private B b;
}
```

```java
public class B {
    private A a;
}
```

```java
/** 放置创建好的bean Map     */    
private static Map<String, Object> cacheMap = new HashMap<>(2);
public static void main(String[] args) {
    // 假装扫描出来的对象        
    Class[] classes = {A.class, B.class};        
    // 假装项目初始化实例化所有bean        
    for (Class aClass : classes) {
    getBean(aClass);        }        
    // check       
    System.out.println(getBean(B.class).getA() == getBean(A.class));
    System.out.println(getBean(A.class).getB() == getBean(B.class)); 
}

@SneakyThrows    
private static <T> T getBean(Class<T> beanClass) {  
    // 本文用类名小写 简单代替bean的命名规则    
    String beanName = beanClass.getSimpleName().toLowerCase();      
    // 如果已经是一个bean，则直接返回      
    if (cacheMap.containsKey(beanName)) {    
        return (T) cacheMap.get(beanName);     
    }      
    // 将对象本身实例化  
    Object object = beanClass.getDeclaredConstructor().newInstance();      
    // 放入缓存  
    cacheMap.put(beanName, object);    
    // 把所有字段当成需要注入的bean，创建并注入到当前bean中      
    Field[] fields = object.getClass().getDeclaredFields();  
    for (Field field : fields) {    
        field.setAccessible(true);        
        // 获取需要注入字段的class     
        Class<?> fieldClass = field.getType();     
        String fieldBeanName = fieldClass.getSimpleName().toLowerCase();   
        // 如果需要注入的bean，已经在缓存Map中，那么把缓存Map中的值注入到该field即可             
        // 如果缓存没有 继续创建       
        field.set(object, cacheMap.containsKey(fieldBeanName)      
        ? cacheMap.get(fieldBeanName) : getBean(fieldClass));   
    }    
    // 属性填充完成，返回   
    return (T) object;   
}



```


