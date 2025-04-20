package com.max;

import java.util.ArrayList;
import java.util.List;

public class MyThreadPool {
    List<Runnable> commandList=new ArrayList<>();

    Thread thread=new Thread(()->{
        while(true){
            commandList.isEmpty();
            Runnable command=commandList.remove(0) ;
            command.run();
        }
    });

    void execute(Runnable command){
        commandList.add(command);
    }
}
