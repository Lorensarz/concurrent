package com.petrov.blocking_queue;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            // Если очередь заполнена, поток ожидает освобождения места
            wait();
        }
        // Добавляем элемент в очередь
        queue.offer(item);
        // Уведомляем другие потоки о появлении нового элемента
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            // Если очередь пуста, поток ожидает появления элементов
            wait();
        }
        // Извлекаем и удаляем элемент из очереди
        T item = queue.poll();
        // Уведомляем другие потоки о возможности добавления новых элементов
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}
