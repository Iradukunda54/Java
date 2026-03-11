package model;

import enums.TaskStatus;
import interfaces.Completable;
import java.util.Arrays;

public abstract class Project {
    private static int idCounter = 0;
    private final String projectId;          // <-- this field must be present
    private String name;
    private String description;
    private double budget;
    private int teamSize;
    private Task[] tasks; // array of tasks, dynamically resized
    private int taskCount;

    public Project(String name, String description, double budget, int teamSize) {
        this.projectId = generateId();        // <-- assignment here
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.teamSize = teamSize;
        this.tasks = new Task[5]; // initial capacity
        this.taskCount = 0;
    }

    private String generateId() {
        idCounter++;
        return String.format("PRJ%03d", idCounter);
    }

    // Abstract method to be implemented by subclasses
    public abstract String getProjectDetails();

    // Concrete method to display project info
    public void displayProject() {
        System.out.println("Project ID: " + projectId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Budget: $" + budget);
        System.out.println("Team Size: " + teamSize);
        System.out.println("Type: " + getProjectType());
        System.out.println("Details: " + getProjectDetails());
        System.out.println("Tasks: " + taskCount);
        System.out.println("Completion: " + String.format("%.2f%%", calculateCompletion()));
    }

    // FIXED: now public (was protected)
    public abstract String getProjectType();

    // Task management
    public void addTask(Task task) {
        if (taskCount == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        tasks[taskCount++] = task;
    }

    public Task[] getTasks() {
        return Arrays.copyOf(tasks, taskCount);
    }

    public Task findTaskById(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getTaskId().equals(taskId)) {
                return tasks[i];
            }
        }
        return null;
    }

    public boolean removeTask(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getTaskId().equals(taskId)) {
                for (int j = i; j < taskCount - 1; j++) {
                    tasks[j] = tasks[j + 1];
                }
                tasks[--taskCount] = null;
                return true;
            }
        }
        return false;
    }

    public double calculateCompletion() {
        if (taskCount == 0) return 0.0;
        int completed = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].isCompleted()) {
                completed++;
            }
        }
        return (completed * 100.0) / taskCount;
    }

    // Getters and setters
    public String getProjectId() { return projectId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
    public int getTeamSize() { return teamSize; }
    public void setTeamSize(int teamSize) { this.teamSize = teamSize; }
}