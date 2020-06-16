## IOC的类型
- **1.构造函数注入**
- **2.属性注入 （get、set）**
- **3.接口注入 （有代码的侵入性）**


## 反射基础
```java
public class ReflectTest{
    public static Car initByDefaultConst() throws Throwable{
        //1.通过类加载器获取Car类对象
        ClassLoader loader = Thread.CurrentThread().getContextClassLoader();
        Class clazz = load.loadClass("com.***.***.Car");
        
        //2.获取类的默认构造器对象并通过它来实例化Car
        Constructor cons = class.getDeclaredConstructor((Class []) null);
        Car car = (Car)cons.newInstance();
        
        //3.通过反射方法设置属性
        Method setBand = clazz.getMethod("setBand",String.class);
        setBand.invoke(car,"红旗CA72");
        Method setColor = clazz.getMethod("setColor",String.class);
        setColor.invoke(car,"黑色");
        ......
        return car;
    }
}
```

## 类装载器ClassLoader
1. 装载：查询和导入Class文件
2. 链接：执行校验、准备和解析。
    - 校验： 检查载入的Class文件数据的正确性
    - 准备： 给类的静态变量分配存储空间
    - 解析： 将符号引用转换成直接引用
3. 初始化： 对类的静态变量、静态代码块执行初始化工作。

JVM在运行的时候会产生三个ClassLoader：根加载器、ExtClassLoader、AppClassLoader
根加载器不是ClassLoader的子类
ExtClassLoader和AppClassLoader都是ClassLoader的子类。
ExtClassLoader： 负责装载JRE ext中的jar包
AppClassLoader： 负责装载Classpath路径下的类包

*父类委托机制可以避免，有人恶意编写基础类（java.lang.String）并装载到Jvm中，有了父类委托机制就永远是调用父类的装载器在装载*

## JAVA反射机制
有三个主要的反射类：
- **Constructor:**
    类的构造函数反射类，通过Class#getConstructors()方法可以获取类的所有构造函数反射对象数组。通过newINstance（），可以创建一个对象类的实例。
- **Method：**
    类方法的反射类，通过Class#getDeclaredMethods()方法可以获取类的所有方法反射对象数组Method[]。最主要的方法是invoke（）。
    - Class getReturnType(): 获取方法的返回值类型。
    - Class[] getParameterTypes(): 获取方法的入参类型数组。
    - Class[] getExceptionTypes(): 获取方法的异常类型数组。
    - Annotation[][] getParameterAnnotations(): 获取方法的注解信息。
- **Filed:**
    类的成员变量的反射类，通过Class#getDeclaredFields()方法可以获取类的成员变量的反射对象数组。  
*在获取private或者protected成员变量和方法的时候，必须通过setAccessible(boolean access)取消java语言的检查，否则会抛出IllegalAccessException.*


## 资源抽象接口 Resource
实现类：  
- WritableResource：可写资源接口。有两个实现类，即FileSystemResource和PathResource。
- ByteArrayResource: 二进制数组资源
- ClassPathResource：类路径下的资源，以==相对路径==的方式表示
- FileSystemResource: 文件资源，资源以文件系统路径的方式表示，如D:/conf/bean.xml
- InputStreamResource: 以输入流返回表示的资源
- ServletContextResource：为访问web容器上下文中的资源而设计的类，负责以相对于web应用根目录的路径加载资源。==该类还可以直接总Jar中直接访问资源==
- UrlResource：URL封装lejava.net.URL, 能访问任何通过URL表示的资源
- PathResource: Spring4.0 提供的新类，使用户能够访问任何可以通过URL,Path,系统文件路径表示的资源，如文件系统的资源，HTTP资源，FTP资源等。

```java
public static void main(String[] args){
    try{
        String filePath = "D:/masterSpring/code/chapter4/src/main/resources/conf/file1.txt";
        
        //1.使用系统文件路径方式加载文件
        WritableResource res1 = new PathResource(filePath);
        
        //2.使用类路径方式加载文件
        Resource res2 = new ClassPathResource("conf/file1.txt");
        
        //3.使用WritableResource接口写资源文件
        OutputStream stream1 = res1.getOutputStream();
        stream1.write("****".getBytes());
        stream1.close();
        
        //4.使用Resource接口读资源文件
        InputStream ins1 = res1.getInputStream();
        InputStream ins2 = res2.getInputStream();
        
        ByteArrayPutPutStream baos = new ByteArrayOutputStream();
        int i = 0;
        while((i=ins1.read()) != -1){
            baos.write(i);
        }
        sout(baos.toString());
    }
    
}
```

## 资源加载
前缀类型：classPath: , file:, http://, ftp://, 没有前缀  
区分classPath:和classPath*: classPath只会在第一个加载的包下查询，classPath* 会扫描所有这些Jar包及以下出现的路径  
ResourceLoader 接口仅支持带资源类型前缀的表达式，不支持Ant风格  
ResourcePatternResolver 支持带资源类型前缀，也支持Ant风格  
**对于打在JAR中的资源文件，我们可以使用Resource#getInputStream()方法获取，如果使用getFile()会抛出FileNotFoundException** 


## BeanFactory 
通过BeanFactory启动IOC容器时，并不会初始化配置文件中定义的bean，初始化动作发生在第一次调用的时候。  
*并且在初始化BEanFactory的时候，需要提供一种日志框架，这样启动才不会报错。*  

## ApplicationContext
主要实现类是：ClassPathXmlApplicationContext 和 FileSystemXmlApplicationContext

 

> 注解说明

- @Autowrite: 自动装配通过类型
- @Resource：自动装配通过名字，类型
- @Nullable： 字段标记了这个注解，说明字段可以为null

