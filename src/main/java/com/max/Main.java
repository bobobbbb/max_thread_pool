package com.max;

public class Main {
    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool();
        myThreadPool.execute(()->{
            System.out.println(Thread.currentThread().getName());
        });
        System.out.println("主线程没有被阻塞");
    }
}
