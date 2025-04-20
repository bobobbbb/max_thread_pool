package com.max;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 一个简单的线程池实现示例（仅用于学习理解）
 */
public class MyThreadPool {

    // 线程安全的阻塞队列，容量为1024
    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1024);

    // 工作线程，不断从队列中取出任务执行
    Thread thread = new Thread(() -> {
        while (true) {
            try {
                // 阻塞式获取任务，没有任务时会等待，不会空转
                Runnable task = blockingQueue.take();
                // 执行任务
                task.run();
            } catch (InterruptedException e) {
                // 处理中断，避免线程意外退出
                Thread.currentThread().interrupt();
                break;
            }
        }
    },"唯一线程");

    // 构造方法中启动线程
    public MyThreadPool() {
        thread.start(); // 一定要启动线程
    }

    /**
     * 将一个任务提交给线程池执行
     */
    public void execute(Runnable command) {
        // 添加任务到队列中（注意：add在队列满时会抛异常，可用put阻塞或offer非阻塞）
        boolean offer = blockingQueue.offer(command);

    }
}
