package com.etatech.test.Java.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michael
 * Date:  2020/3/25
 * Func:
 */
public class RxJavaTest {

    public static void main(String[] args) {
        Test1();
    }

    public static void Test1() {
        // 方法1
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });
        // 方法2
        // Observable observable1 = Observable.just("A", "B", "C");
        // 方法3
        // String[] words = { "Q", "W", "E", "R" };
        // Observable observable2 = Observable.fromArray(words);
        // 方法4
        // Observable<Long> observable3 = Observable.interval(1, 1, TimeUnit.SECONDS);

        // 方法1
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext:" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        // 方法2
        // Subscriber<String> observer2 = new Subscriber<String>() {

        //     @Override
        //     public void onSubscribe(Subscription s) {
        //         System.out.println("onSubscribe");
        //     }

        //     @Override
        //     public void onNext(String integer) {
        //         System.out.println("onNext:" + integer);
        //     }

        //     @Override
        //     public void onError(Throwable t) {
        //         System.out.println("onError");
        //     }

        //     @Override
        //     public void onComplete() {
        //         System.out.println("onComplete");
        //     }
        // };

        observable.subscribe(observer);

    }

    public static void Test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(4);
                e.onNext(5);
                e.onNext(6);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("onNext" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    // 遍历列表
    public static void Test3() {
        Observable.just("Hello", "World").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        Integer[] nums = {1, 2, 3, 4, 5, 6};
        Observable.fromArray(nums).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                System.out.println(s);
            }
        });

        List<String> listString = new ArrayList<>();
        listString.add("item1");
        listString.add("item2");
        listString.add("item3");

        Observable.fromIterable(listString).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    // 延时执行
    public static void Test5() {
        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Long value) {
                System.out.println("延迟了:" + value + "秒.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    // 固定间隔执行
    public static void Test6() {
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
                .take(6)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }
}