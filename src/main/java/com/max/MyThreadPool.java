package com.max;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个简单的线程池实现示例（仅用作学习理解）
 */
public class MyThreadPool {
    // 用于存放提交过来的任务
    List<Runnable> commandList = new ArrayList<>();
    // 启动一个工作线程，不断从任务列表中取任务执行
    Thread thread = new Thread(() -> {
        while (true) {
            // 这里存在 bug：仅调用 isEmpty() 并没有等待或跳过空任务情况，应加判断或等待
            commandList.isEmpty();
            // 从任务队列中取出第一个任务
            Runnable command = commandList.remove(0);
            // 执行任务
            command.run();
        }
    });

    /**
     * 将一个任务提交给线程池执行
     */
    void execute(Runnable command) {
        // 添加任务到任务列表中
        commandList.add(command);
    }
}
