package com.max;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        // 创建自定义线程池对象
        MyThreadPool myThreadPool = new MyThreadPool(1, TimeUnit.SECONDS, 2, 4, new ArrayBlockingQueue<>(2));
        // 向线程池提交5个任务
        for (int i = 0; i < 5; i++) {
            myThreadPool.execute(() -> {
                try {
                    // 模拟任务执行时间
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // 输出当前执行任务的线程名称
                System.out.println(Thread.currentThread().getName());
            });
        }
    }

}
