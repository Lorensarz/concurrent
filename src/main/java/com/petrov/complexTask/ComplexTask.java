package com.petrov.complexTask;

public class ComplexTask {
    private int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        //Здесь выполняется большая и сложная задача!
        System.out.println("Task " + taskId + " is executing.");
    }
}
