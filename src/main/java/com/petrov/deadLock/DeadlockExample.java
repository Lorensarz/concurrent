package com.petrov.deadLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {

    private static class Resource {
        // Ресурсы
    }

    private final Resource resourceA = new Resource();
    private final Resource resourceB = new Resource();
    private final Lock lockA = new ReentrantLock();
    private final Lock lockB = new ReentrantLock();

    public void execute() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                acquireResourcesAndWork(lockA, lockB, resourceA, resourceB, "Thread-1");
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                acquireResourcesAndWork(lockB, lockA, resourceB, resourceA, "Thread-2");
            }
        });

        thread1.start();
        thread2.start();
    }
    private void acquireResourcesAndWork(Lock firstLock, Lock secondLock, Resource firstResource,
                                         Resource secondResource, String threadName) {
        boolean gotFirstLock = false;
        boolean gotSecondLock = false;

        try {
            gotFirstLock = firstLock.tryLock(100, TimeUnit.MILLISECONDS);
            if (gotFirstLock) {
                System.out.println(threadName + " locked " + firstResource);

                // Имитация работы с ресурсом
                Thread.sleep(100);

                gotSecondLock = secondLock.tryLock(100, TimeUnit.MILLISECONDS);
                if (gotSecondLock) {
                    System.out.println(threadName + " locked " + secondResource);

                    // Имитация работы с ресурсом
                    Thread.sleep(100);
                } else {
                    System.out.println(threadName + " could not acquire second lock, releasing first lock");
                }
            } else {
                System.out.println(threadName + " could not acquire first lock");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (gotSecondLock) {
                secondLock.unlock();
                System.out.println(threadName + " unlocked " + secondResource);
            }
            if (gotFirstLock) {
                firstLock.unlock();
                System.out.println(threadName + " unlocked " + firstResource);
            }
        }
    }

    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();
        example.execute();
    }
}
