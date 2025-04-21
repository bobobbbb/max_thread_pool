package com.max;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 一个简单的线程池实现示例（仅用于学习理解）
 */
public class MyThreadPool {

    // 线程安全的阻塞队列，容量为1024
    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1024);

    private final Runnable task=() -> {
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
    };
    //核心的线程数默认为10
    private int corePoolSize=10;

    //最大线程容量
    private int maxSize=16;

    //线程集合
    List<Thread> coreList=new ArrayList<>();

    //支持线程
    List<Thread> supportList=new ArrayList<>();


    /**
     * 将一个任务提交给线程池执行
     */
    public void execute(Runnable command) {
        //我们判断这个list中有多少个线程，如果线程数小于core就创建一个线程
        if(coreList.size()<corePoolSize){
            //创建一个线程
            Thread thread = new Thread(task);
            //添加进线程中
            coreList.add(thread);
            //执行命令
            thread.start();
        }
        //如果大于核心线程数，我们就放入阻塞队列
        if(blockingQueue.offer(command)){
            return;
        }
        //如果队列满了，就新建线程来完成任务
        if(coreList.size()+supportList.size()<maxSize){
            Thread thread = new Thread(task);
            supportList.add(thread);
            thread.start();
        }
        if(!blockingQueue.offer(command)){
            throw new RuntimeException("阻塞队列满了");
        }
    }
}
