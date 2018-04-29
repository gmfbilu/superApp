// Book.aidl
//第一类AIDL文件
//这个文件的作用是引入了一个序列化对象 Book 供其他的AIDL文件使用

//注意：Book.aidl与Book.java的包名应当是一样的.Gradle 默认是将 java 代码的访问路径设置在 java 包下的
//修改 build.gradle 文件：在 android{} 中间加上下面的内容：
//sourceSets { main {java.srcDirs = ['src/main/java', 'src/main/aidl']}}


package org.gmfbilu.superapp.module_util.aidl;



parcelable Book;



