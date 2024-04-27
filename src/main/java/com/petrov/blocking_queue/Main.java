package com.petrov.blocking_queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // Создаем блокирующую очередь с фиксированным размером 5
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);

        // Создаем пул потоков с двумя потоками
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Производитель добавляет элементы в очередь
        executor.submit(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.enqueue(i);
                    System.out.println("Добавлен элемент: " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Потребитель извлекает элементы из очереди
        executor.submit(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    int item = queue.dequeue();
                    System.out.println("Извлечен элемент: " + item);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Завершаем работу пула потоков после завершения задач
        executor.shutdown();
    }
}
