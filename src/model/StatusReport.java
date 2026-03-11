package model;

public class StatusReport {
    private String projectId;
    private String projectName;
    private int totalTasks;
    private int completedTasks;
    private double completionPercentage;

    public StatusReport(String projectId, String projectName, int totalTasks, int completedTasks) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.completionPercentage = (totalTasks == 0) ? 0 : (completedTasks * 100.0 / totalTasks);
    }

    public String getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public int getTotalTasks() { return totalTasks; }
    public int getCompletedTasks() { return completedTasks; }
    public double getCompletionPercentage() { return completionPercentage; }

    @Override
    public String toString() {
        return String.format("Project %s (%s): %d/%d tasks completed (%.2f%%)",
                projectName, projectId, completedTasks, totalTasks, completionPercentage);
    }
}