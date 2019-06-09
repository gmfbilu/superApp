package org.gmfbilu.superapp.module_java.basic.反射;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Reflect {

    public static void main(String[] a) {
        Reflect reflect = new Reflect();
        reflect.hello10();
    }


    /**
     * 通过对象实例获取对应Class对象,对于基本类型无法使用这种方法
     */
    private void hello1() {
        //Returns the Class for String
        Class a = "foo".getClass();

        //Returns the Class corresponding to an array with component type byte.
        byte[] bytes = new byte[1024];
        Class b = bytes.getClass();

        //Returns the Class corresponding to java.util.HashSet.
        Set<String> hashSet = new HashSet<>();
        Class c = hashSet.getClass();
    }

    /**
     * 通过类的类型获取Class对象,基本类型可以使用这种方法
     */
    private void hello2() {
        //The `.class` syntax returns the Class corresponding to the type `boolean`.
        Class a = boolean.class;

        //Returns the Class for String
        Class b = String.class;
    }

    /**
     * 通过类的全限定名获取Class对象， 基本类型无法使用此方法
     */
    private void hello3() {
        //通过Class.forName()方法加载的类，采用的是系统类加载器
        try {
            Class a = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //对于数组比较特殊,相当于double[].class
        try {
            Class b = Class.forName("[D");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //相当于String[][].class
        try {
            Class c = Class.forName("[[Ljava.lang.String;");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本类型和void类型的包装类可以使用TYPE字段获取Class对象
     */
    private void hello4() {
        //等价于 double.class.
        Class a = Double.TYPE;
        Class b = Void.TYPE;
    }

    /**
     * java.util.HashMap
     * public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable
     */
    private void hello5() {
        Class<?> hashMapClass = HashMap.class;
        //获取类名.java.util.HashMap
        System.out.println(hashMapClass.getCanonicalName());
        //获取类限定符.public
        System.out.println(Modifier.toString(hashMapClass.getModifiers()));
        //获取类泛型信息.K V
        TypeVariable[] tv = hashMapClass.getTypeParameters();
        if (tv.length != 0) {
            StringBuilder parameter = new StringBuilder("Parameters : ");
            for (TypeVariable t : tv) {
                parameter.append(t.getName());
                parameter.append(" ");
            }
            System.out.println(parameter.toString());
        } else {
            System.out.println("  -- No Type Parameters --");
        }
        //获取类实现的所有接口.java.util.Map<K, V>,interface java.lang.Cloneable,interface java.io.Serializable
        Type[] intfs = hashMapClass.getGenericInterfaces();
        if (intfs.length != 0) {
            for (Type intf : intfs) {
                System.out.println("Implemented Interfaces :" + intf.toString());
            }
        } else {
            System.out.println(" -- No Implemented Interfaces --");
        }
        //获取类继承数上的所有父类. java.util.AbstractMap
        List<Class> l = new ArrayList<>();
        Class<?> ancestor = hashMapClass.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
        }
        if (l.size() != 0) {
            StringBuilder inheritance = new StringBuilder("Inheritance Path : ");
            for (Class<?> cl : l) {
                inheritance.append(cl.getCanonicalName());
                inheritance.append(" ");
            }
            System.out.println(inheritance.toString());
        } else {
            System.out.println("-- No Super Classes --%n%n");
        }
        //获取类的注解(只能获取到 RUNTIME 类型的注解)
        Annotation[] ann = hashMapClass.getAnnotations();
        if (ann.length != 0) {
            StringBuilder annotation = new StringBuilder("Annotations : ");
            for (Annotation a : ann) {
                annotation.append(a.toString());
                annotation.append(" ");
            }
            System.out.println(" annotation.toString()");
        } else {
            System.out.println(" -- No Annotations --%n%n");
        }
    }

    /**
     * 类成员主要包括构造函数，变量和方法，Java中的操作基本都和这三者相关，而Member的这三个实现类就分别对应他们
     * java.lang.reflect.Field ：对应类变量
     * java.lang.reflect.Method ：对应类方法
     * java.lang.reflect.Constructor ：对应类构造函数
     * 反射就是通过这三个类才能在运行时改变对象状态
     * <p>
     * Java运行时会进行访问权限检查，private类型的变量无法进行直接访问
     * 任何继承AccessibleObject的类的对象都可以使用 setAccessible(boolean flag)取消 Java 语言访问权限检查
     * Field、Method和Constructor都是继承AccessibleObject
     */
    private void hello6() {
        hello7();
        hello8();
    }

    /**
     * 通过Field你可以访问给定对象的类变量，包括获取变量的类型、修饰符、注解、变量名、变量的值或者重新设置变量值，即使变量是private的
     * Class提供了4种方法获得给定类的Field
     * getDeclaredField(String name):获取指定的变量（只要是声明的变量都能获得，包括private）
     * getField(String name):获取指定的变量（只能获得public的）
     * getDeclaredFields():获取所有声明的变量（包括private）
     * getFields():
     * 获取所有的public变量
     */
    private void hello7() {
        // public static final String TAG
        // private String name;
        // @Deprecated
        // public int age;
        Class<Cat> catClass = Cat.class;
        Field[] fields = catClass.getDeclaredFields();
        for (Field f : fields) {
            StringBuilder builder = new StringBuilder();
            //获取名称.TAG,name,age
            builder.append("filed = ");
            builder.append(f.getName());
            //获取类型. class java.lang.String,class java.lang.String,int
            builder.append(", type = ");
            builder.append(f.getType());
            //获取修饰符.public static final,private,public
            builder.append(", modifiers = ");
            builder.append(Modifier.toString(f.getModifiers()));
            //获取注解.@java.lang.Deprecated()
            Annotation[] ann = f.getAnnotations();
            if (ann.length != 0) {
                builder.append(" annotations = ");
                for (Annotation a : ann) {
                    builder.append(a.toString());
                    builder.append(" ");
                }
            } else {
                builder.append("  -- No Annotations --");
            }
            System.out.println(builder.toString());
        }
    }

    /**
     * 通过反射获取并改变Cat的name和age
     */
    private void hello8() {
        Cat cat = new Cat("Tom", 2);
        Class c = cat.getClass();
        try {
            //注意获取private变量时，需要用getDeclaredField
            Field fieldName = c.getDeclaredField("name");
            fieldName.setAccessible(true);
            Field fieldAge = c.getField("age");
            //反射获取名字, 年龄
            String name = (String) fieldName.get(cat);
            int age = fieldAge.getInt(cat);
            System.out.println("before set, Cat name = " + name + " age = " + age);
            //反射重新set名字和年龄
            fieldName.set(cat, "Timmy");
            fieldAge.setInt(cat, 3);
            System.out.println("after set, Cat " + cat.toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class依然提供了4种方法获取Method:
     * 获取带参数方法时，如果参数类型错误会报NoSuchMethodException，对于参数是泛型的情况，泛型须当成Object处理（Object.class）
     * getDeclaredMethod(String name, Class<?>... parameterTypes):根据方法名获得指定的方法， 参数name为方法名，参数parameterTypes为方法的参数类型，如 getDeclaredMethod(“eat”, String.class)
     * getMethod(String name, Class<?>... parameterTypes):根据方法名获取指定的public方法，其它同上
     * getDeclaredMethods():获取所有声明的方法
     * getMethods():获取所有的public方法
     * <p>
     * 获取方法返回类型:
     * getReturnType() :获取目标方法返回类型对应的Class对象
     * getGenericReturnType(): 获取目标方法返回类型对应的Type对象
     * getReturnType()返回类型为Class，getGenericReturnType()返回类型为Type; Class实现Type
     * 返回值为普通简单类型如Object, int, String等，getGenericReturnType()返回值和getReturnType()一样
     * 返回值为泛型,例如public T function2().那么各自返回值为：getReturnType() : class java.lang.Object.   getGenericReturnType() : T
     * 返回值为参数化类型,例如public Class<String> function3().那么各自返回值为：getReturnType() : class java.lang.Class.   getGenericReturnType() : java.lang.Class<java.lang.String>
     * <p>
     * 获取方法参数类型
     * getParameterTypes() 获取目标方法各参数类型对应的Class对象
     * getGenericParameterTypes() 获取目标方法各参数类型对应的Type对象,返回值为数组，它俩区别同上 “方法返回类型的区别”
     * <p>
     * 获取方法声明抛出的异常的类型
     * getExceptionTypes() 获取目标方法抛出的异常类型对应的Class对象
     * getGenericExceptionTypes() 获取目标方法抛出的异常类型对应的Type对象,返回值为数组，区别同上
     * <p>
     * 获取方法参数名称
     * .class文件中默认不存储方法参数名称，如果想要获取方法参数名称，需要在编译的时候加上-parameters参数。(构造方法的参数获取方法同样)
     * <p>
     * 获取方法修饰符,方法与Filed等类似
     * <p>
     * 几个Method方法
     * method.isVarArgs() //判断方法参数是否是可变参数
     * method.isSynthetic() //判断是否是复合方法，个人理解复合方法是编译期间编译器生成的方法，并不是源代码中有的方法
     * method.isBridge() //判断是否是桥接方法，桥接方法是 JDK 1.5 引入泛型后，为了使Java的泛型方法生成的字节码和 1.5 版本前的字节码相兼容，由编译器自动生成的方法
     * <p>
     * 通过反射调用方法
     * 反射通过Method的invoke()方法来调用目标方法。第一个参数为需要调用的目标类对象，如果方法为static的，则该参数为null。后面的参数都为目标方法的参数值，顺序与目标方法声明中的参数顺序一致
     * public native Object invoke(Object obj, Object... args)throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
     * 方法是private的，可以使用method.setAccessible(true)方法绕过权限检查
     * <p>
     * 被调用的方法本身所抛出的异常在反射中都会以InvocationTargetException抛出。换句话说，反射调用过程中如果异常InvocationTargetException抛出，说明反射调用本身是成功的，因为这个异常是目标方法本身所抛出的异常
     */
    private void hello9() {
        Class<?> c = Cat.class;
        try {
            //构造Cat实例
            Constructor constructor = c.getConstructor(String.class, int.class);
            Object cat = constructor.newInstance("Jack", 3);
            //调用无参方法
            Method sleep = c.getDeclaredMethod("sleep");
            sleep.invoke(cat);
            //调用定项参数方法
            Method eat = c.getDeclaredMethod("eat", String.class);
            eat.invoke(cat, "grass");
            //调用不定项参数方法
            //不定项参数可以当成数组来处理
            Class[] argTypes = new Class[]{String[].class};
            Method varargsEat = c.getDeclaredMethod("eat", argTypes);
            String[] foods = new String[]{
                    "grass", "meat"
            };
            varargsEat.invoke(cat, (Object) foods);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射访问构造方法并通过构造方法构建新的对象
     * 获取构造方法
     * 和Method一样，Class也为Constructor提供了4种方法获取
     * getDeclaredConstructor(Class<?>... parameterTypes):获取指定构造函数，参数parameterTypes为构造方法的参数类型
     * getConstructor(Class<?>... parameterTypes):获取指定public构造函数，参数parameterTypes为构造方法的参数类型
     * getDeclaredConstructors():获取所有声明的构造方法
     * getConstructors():获取所有的public构造方法
     * <p>
     * 构造方法的名称、限定符、参数、声明的异常等获取方法都与Method类似
     * <p>
     * 创建对象
     * 通过反射有两种方法可以创建对象：
     * java.lang.reflect.Constructor.newInstance()
     * Class.newInstance()
     * 一般来讲，优先使用第一种方法
     * Class.newInstance()仅可用来调用无参的构造方法；Constructor.newInstance()可以调用任意参数的构造方法
     * Class.newInstance()会将构造方法中抛出的异常不作处理原样抛出;Constructor.newInstance()会将构造方法中抛出的异常都包装成InvocationTargetException抛出
     * Class.newInstance()需要拥有构造方法的访问权限;Constructor.newInstance()可以通过setAccessible(true)方法绕过访问权限访问private构造方法
     * <p>
     * 反射不支持自动封箱，传入参数时要小心（自动封箱是在编译期间的，而反射在运行期间）
     */
    private void hello10() {
        Class<?> c = Cat.class;
        try {
            Constructor constructor = c.getConstructor(String.class, int.class);
            Cat cat = (Cat) constructor.newInstance("Jack", 3);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数组和枚举
     * 数组和枚举也是对象，但是在反射中，对数组和枚举的创建、访问和普通对象有那么一丢丢的不同，所以Java反射为数组和枚举提供了一些特定的API接口
     * 数组:
     * 数组类型：数组本质是一个对象，所以它也有自己的类型
     * 例如对于int[] intArray，数组类型为class [I。数组类型中的[个数代表数组的维度，例如[代表一维数组，[[代表二维数组；[后面的字母代表数组元素类型，I代表int，一般为类型的首字母大写(long类型例外，为J)
     * class [B    //byte类型一维数组
     * class [S    //short类型一维数组
     * class [I    //int类型一维数组
     * class [C    //char类型一维数组
     * class [J    //long类型一维数组，J代表long类型，因为L被引用对象类型占用了
     * class [F    //float类型一维数组
     * class [D    //double类型一维数组
     * class [Lcom.dada.Season    //引用类型一维数组
     * class [[Ljava.lang.String  //引用类型二维数组
     * <p>
     * 枚举
     * 枚举隐式继承自java.lang.Enum，Enum继承自Object，所以枚举本质也是一个类，也可以有成员变量，构造方法，方法等；对于普通类所能使用的反射方法，枚举都能使用；另外java反射额外提供了几个方法为枚举服务
     */
    private void hello11() {
        Class<Cat> catClass = Cat.class;
        /**
         * 注解中常用的方法
         */
        Annotation[] annotations = (Annotation[]) catClass.getAnnotations();//获取class对象的所有注解
        Annotation annotation = (Annotation) catClass.getAnnotation(Deprecated.class);//获取class对象指定注解
        Type genericSuperclass = catClass.getGenericSuperclass();//获取class对象的直接超类的
        Type[] genericInterfaces = catClass.getGenericInterfaces();//获取class对象的所有接口的type集合

        /**
         * 获取Class对象其它信息的方法
         */
        boolean isPrimitive = catClass.isPrimitive();//判断是否是基础类型
        boolean isArray = catClass.isArray();//判断是否是集合类
        boolean isAnnotation = catClass.isAnnotation();//判断是否是注解类
        boolean isInterface = catClass.isInterface();//判断是否是接口类
        boolean isEnum = catClass.isEnum();//判断是否是枚举类
        boolean isAnonymousClass = catClass.isAnonymousClass();//判断是否是匿名内部类
        boolean isAnnotationPresent = catClass.isAnnotationPresent(Deprecated.class);//判断是否被某个注解类修饰
        String className = catClass.getName();//获取class名字 包含包名路径
        Package aPackage = catClass.getPackage();//获取class的包信息
        String simpleName = catClass.getSimpleName();//获取class类名
        int modifiers = catClass.getModifiers();//获取class访问权限
        Class<?>[] declaredClasses = catClass.getDeclaredClasses();//内部类
        Class<?> declaringClass = catClass.getDeclaringClass();//外部类
        ClassLoader ClassLoader = catClass.getClassLoader(); //返回类加载器
    }

}
