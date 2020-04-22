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
- 
 