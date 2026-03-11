package model;

import enums.TaskStatus;
import interfaces.Completable;

public class Task implements Completable {
    private static int idCounter = 0;
    private final String taskId;
    private String name;
    private TaskStatus status;
    private String assignedUserId; // optional, can be null

    public Task(String name, TaskStatus status) {
        this.taskId = generateId();
        this.name = name;
        this.status = status;
    }

    private String generateId() {
        idCounter++;
        return String.format("TSK%03d", idCounter);
    }

    @Override
    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }

    // Getters and setters
    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public String getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(String assignedUserId) { this.assignedUserId = assignedUserId; }
}