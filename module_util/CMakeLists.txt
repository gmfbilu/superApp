cmake_minimum_required(VERSION 3.4.1)
add_library( # Sets the name of the library.cpp文件名
             jniDemo

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).cpp文件地址
             src/main/cpp/jniDemo.cpp )

target_link_libraries(
                        #cpp文件名
                        jniDemo
                        android
                        log)
