package services;

import model.Project;
import model.SoftwareProject;
import model.HardwareProject;
import java.util.Arrays;

public class ProjectService {
    private Project[] projects;
    private int projectCount;

    public ProjectService() {
        projects = new Project[5]; // initial capacity
        projectCount = 0;
    }

    public void addProject(Project project) {
        if (projectCount == projects.length) {
            projects = Arrays.copyOf(projects, projects.length * 2);
        }
        projects[projectCount++] = project;
    }

    public Project[] getAllProjects() {
        return Arrays.copyOf(projects, projectCount);
    }

    public Project findProjectById(String projectId) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getProjectId().equals(projectId)) {
                return projects[i];
            }
        }
        return null;
    }

    public boolean deleteProject(String projectId) {
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getProjectId().equals(projectId)) {
                // shift left
                for (int j = i; j < projectCount - 1; j++) {
                    projects[j] = projects[j + 1];
                }
                projects[--projectCount] = null;
                return true;
            }
        }
        return false;
    }

    public Project[] filterByType(String type) {
        Project[] temp = new Project[projectCount];
        int count = 0;
        for (int i = 0; i < projectCount; i++) {
            if (projects[i].getProjectType().equalsIgnoreCase(type)) {
                temp[count++] = projects[i];
            }
        }
        return Arrays.copyOf(temp, count);
    }

    public Project[] filterByBudgetRange(double min, double max) {
        Project[] temp = new Project[projectCount];
        int count = 0;
        for (int i = 0; i < projectCount; i++) {
            double budget = projects[i].getBudget();
            if (budget >= min && budget <= max) {
                temp[count++] = projects[i];
            }
        }
        return Arrays.copyOf(temp, count);
    }
}