package services;

import model.Project;
import model.Task;
import enums.TaskStatus;

public class ReportService {
    private ProjectService projectService;

    public ReportService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void displayProjectCompletion(String projectId) {
        Project project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        double percentage = project.calculateCompletion();
        Task[] tasks = project.getTasks();
        int total = tasks.length;
        int completed = 0;
        int inProgress = 0;
        int pending = 0;
        for (Task t : tasks) {
            switch (t.getStatus()) {
                case COMPLETED: completed++; break;
                case IN_PROGRESS: inProgress++; break;
                case PENDING: pending++; break;
            }
        }
        System.out.println("=== Completion Report for Project: " + project.getName() + " ===");
        System.out.println("Project ID: " + project.getProjectId());
        System.out.println("Total Tasks: " + total);
        System.out.println("Completed: " + completed);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Pending: " + pending);
        System.out.printf("Completion Percentage: %.2f%%\n", percentage);
    }

    public void displayAllProjectsSummary() {
        Project[] projects = projectService.getAllProjects();
        System.out.println("=== All Projects Summary ===");
        for (Project p : projects) {
            System.out.printf("%s | %s | Type: %s | Tasks: %d | Completion: %.2f%%\n",
                    p.getProjectId(), p.getName(), p.getProjectType(),
                    p.getTasks().length, p.calculateCompletion());
        }
    }
}