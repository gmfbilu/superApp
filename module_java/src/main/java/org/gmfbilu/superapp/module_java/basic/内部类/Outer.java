package org.gmfbilu.superapp.module_java.basic.内部类;


import android.view.View;
import android.widget.Button;

class Outer {

    private static int a;
    private int b;

    private void h(){
        int in = 0;
    }

    private void m() {
        //可以直接访问成员方法
        mInner.get();
        //可以直接访问非静态成员属性
        int c = mInner.c;

        Inner2.gg();
        int xx = Inner2.xx;

        new Button(null).setOnClickListener(new View.OnClickListener() {

            final int aaa=0;
           public int a1;
           //不能定义静态成员变量
            //static int a2;

            //public static void aaaa(){}

            @Override
            public void onClick(View v) {
                //可以访问外部类静态成员变量
                int a = Outer.a;
                int b = Outer.this.b;
                m();
                j();
                gete();
            }

            private void gete(){

            }

            {

            }


        });
    }

    private static void j() {
        {
            class A {

                private void ge() {
                    //可以直接访问外部类静态成员变量
                    int bb = a;
                    // m();
                    j();
                    // int b1 = b;

                    {
                        class B {

                        }
                    }
                }
            }
        }

    }


    {
        class AA{
            int cc = 0;

            private void ge() {

                int bb = a;
                m();
                j();
                int b1 = b;
                int cc1 = this.cc;
                {
                    class B {

                    }
                }
            }
        }
    }

    private void test() {


            class A {
                int cc = 0;

                private void ge() {

                    int bb = a;
                    m();
                    j();
                    int b1 = b;
                    int cc1 = this.cc;
                    {
                        class B {

                        }
                    }
                }


            }


    }

    Inner mInner = new Inner();
    static Inner2 mInner2 = new Inner2();


    public static void main(String[] a) {
        int aaa = mInner2.x;
        mInner2.g();
        Inner2.gg();
    }

    class Inner {

        final static int aa = 0;
        int c = 1;

        private int get() {
            //直接调用外部类成员方法(不管是不是静态)
            j();
            m();
            //可以直接访问外部类成员变量(不管是不是静态)
            return a + b;
        }


    }

    static class Inner2 {
        //直接访问外部类静态成员变量
        int x = a;
        static int xx = 0;

        private void g() {
            //直接访问外部类静态成员方法
            j();
        }

        private static void gg() {
        }

        public static void main(String[] a) {
        }
    }


}
