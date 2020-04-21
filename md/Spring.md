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
