cmake_minimum_required(VERSION 3.4.1)
add_library( # Sets the name of the library.cpp文件名
             jniDemo

             # 设置这个so库为共享
             SHARED

             # Provides a relative path to your source file(s).cpp文件地址
             src/main/cpp/jniDemo.cpp )

target_link_libraries(
                        #cpp文件名
                        jniDemo
                        android
                        log)

add_library(jniDemo2
            SHARED
            src/main/cpp/jniDemo2.cpp )

target_link_libraries(jniDemo2
        android
        log)
