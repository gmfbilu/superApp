package org.gmfbilu.superapp.module_util.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.module_util.BookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务端的AIDLService.java
 */

public class AIDLService extends Service {


    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();

    //由AIDL文件生成的BookManager
    private final BookManager.Stub mBookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() {
            synchronized (this) {
                Logger.d("invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }
        }


        @Override
        public void addBook(Book book) {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Logger.d("Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.name="尝试修改book的参数，主要是为了观察其到客户端的反馈";
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Logger.d("invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.name = "Android开发艺术探索";
        mBooks.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(String.format("on bind,intent = %s", intent.toString()));
        return mBookManager;
    }

}
