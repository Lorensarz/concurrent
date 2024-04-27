package com.petrov.complexTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private final ExecutorService executor;
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        executor = Executors.newFixedThreadPool(numberOfTasks);
        barrier = new CyclicBarrier(numberOfTasks, () -> System.out.println("All tasks completed."));
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            int taskId = i;
            executor.execute(() -> {
                ComplexTask task = new ComplexTask(taskId);
                task.execute();
                try {
                    barrier.await(); // Поток ждет, пока все задачи не завершатся
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
