package com.backend.singleton;

import com.backend.entidades.Post;
import com.backend.repositorios.PostRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ThreadSingleton implements Runnable {
    int timeSleep;
    PostRepositorio postRepositorio;
    List<Post> postList;
    private static final Logger log = LoggerFactory.getLogger(ConfiguradorSingleton.class);

    public ThreadSingleton (int timeSleep, PostRepositorio postRepositorio,  List<Post> postList){
        this.timeSleep = timeSleep;
        this.postList = postList;
        this.postRepositorio = postRepositorio;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(this.timeSleep);
                synchronized (this.postList){
                    this.postList = this.postRepositorio.findAll();
                }
                log.info(" LIST SIZE: "+this.postList.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
