package com.max;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 一个简单的线程池实现示例（仅用于学习理解）
 */
public class MyThreadPool {

    //默认超时时间是
    private final int timeout;

    //默认时间单位
    private final TimeUnit timeUnit;
    //核心的线程
    private final int corePoolSize;

    //最大线程容量
    private final int maxSize;

    //线程集合
    List<Thread> coreList=new ArrayList<>();

    //支持线程
    List<Thread> supportList=new ArrayList<>();

    public MyThreadPool(int timeout, TimeUnit timeUnit, int corePoolSize, int maxSize, BlockingQueue<Runnable> blockingQueue) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.corePoolSize = corePoolSize;
        this.maxSize = maxSize;
        this.blockingQueue = blockingQueue;
    }

    // 线程安全的阻塞队列，容量为1024
    private final BlockingQueue<Runnable> blockingQueue;
    /**
     * 将一个任务提交给线程池执行
     */
    public void execute(Runnable command) {
        //我们判断这个list中有多少个线程，如果线程数小于core就创建一个线程
        if(coreList.size()<corePoolSize){
            //创建一个线程
            Thread thread = new CoreThread();
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
            Thread thread = new SupportThread();
            supportList.add(thread);
            thread.start();
        }
        if(!blockingQueue.offer(command)){
            throw new RuntimeException("阻塞队列满了");
        }
    }
    class CoreThread extends Thread{
        @Override
        public void run(){
            while (true) {
                try {
                    // 阻塞式获取任务，没有任务时会等待，不会空转
                    Runnable command = blockingQueue.take();
                    // 执行任务
                    command.run();
                } catch (InterruptedException e) {
                    // 处理中断，避免线程意外退出
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    class SupportThread extends Thread{
        @Override
        public void run(){
            while (true) {
                try {
                    // 阻塞式获取任务，没有任务时会等待，不会空转
                    Runnable command = blockingQueue.poll(timeout,timeUnit);
                    if(command==null){
                        break;
                    }
                    // 执行任务
                    command.run();
                } catch (InterruptedException e) {
                    // 处理中断，避免线程意外退出
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName()+"辅助线程结束");
        }
    }
}
