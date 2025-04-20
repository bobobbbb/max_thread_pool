package com.max;

public class Main {
    public static void main(String[] args) {
        // 创建自定义线程池对象
        MyThreadPool myThreadPool = new MyThreadPool();

        // 向线程池提交5个任务
        for (int i = 0; i < 5; i++) {
            myThreadPool.execute(() -> {
                try {
                    // 模拟任务执行时间
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // 输出当前执行任务的线程名称
                System.out.println(Thread.currentThread().getName());
            });
        }

        // 主线程继续执行，没有被阻塞
        System.out.println("主线程没有被阻塞");
    }
}
