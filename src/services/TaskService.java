package services;

import enums.TaskStatus;
import model.Project;
import model.Task;
import utils.ValidationUtils;

public class TaskService {
    private ProjectService projectService;

    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public boolean addTaskToProject(String projectId, String taskName, TaskStatus status, String assignedUserId) {
        Project project = projectService.findProjectById(projectId);
        if (project == null) return false;

        // Check for duplicate task name (case-insensitive)
        for (Task t : project.getTasks()) {
            if (t.getName().equalsIgnoreCase(taskName)) {
                return false; // duplicate
            }
        }

        Task task = new Task(taskName, status);
        task.setAssignedUserId(assignedUserId);
        project.addTask(task);
        return true;
    }

    public boolean updateTaskStatus(String projectId, String taskId, TaskStatus newStatus) {
        Project project = projectService.findProjectById(projectId);
        if (project == null) return false;

        Task task = project.findTaskById(taskId);
        if (task == null) return false;

        task.setStatus(newStatus);
        return true;
    }

    public boolean deleteTask(String projectId, String taskId) {
        Project project = projectService.findProjectById(projectId);
        if (project == null) return false;

        return project.removeTask(taskId);
    }

    public Task[] getTasksForProject(String projectId) {
        Project project = projectService.findProjectById(projectId);
        if (project == null) return new Task[0];
        return project.getTasks();
    }
}