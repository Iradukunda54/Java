package utils;

import enums.TaskStatus;
import model.*;
import services.*;
import java.util.Scanner;

public class ConsoleMenu {
    private Scanner scanner;
    private ProjectService projectService;
    private TaskService taskService;
    private UserService userService;
    private ReportService reportService;
    private User currentUser;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        projectService = new ProjectService();
        taskService = new TaskService(projectService);
        userService = new UserService();
        reportService = new ReportService(projectService);
    }

    public void start() {
        login();
        int choice;
        do {
            displayMainMenu();
            choice = ValidationUtils.readIntInRange("Enter your choice: ", 1, 7);
            switch (choice) {
                case 1 -> manageProjects();
                case 2 -> manageTasks();
                case 3 -> manageUsers();
                case 4 -> viewReports();
                case 5 -> browseProjects();
                case 6 -> statusReports();
                case 7 -> System.out.println("Exiting... Goodbye!");
            }
        } while (choice != 7);
    }

    // -------------------- LOGIN --------------------
    private void login() {
        System.out.println("\n=== Welcome to Project/Task Management System ===");
        User[] users = userService.getAllUsers();

        if (users.length == 0) {
            System.out.println("No users found. Let's create the first admin user.");
            String name = ValidationUtils.readNonEmptyString("Enter admin name: ");
            String email = ValidationUtils.readNonEmptyString("Enter admin email: ");
            AdminUser admin = new AdminUser(name, email);
            userService.addUser(admin);
            System.out.println("Admin user created with ID: " + admin.getUserId());
            currentUser = admin;
            System.out.println("Logged in as: " + currentUser.getName() + " (Admin)");
            return;
        }

        System.out.println("Select a user to log in:");
        for (int i = 0; i < users.length; i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, users[i].getName(), users[i].getRole());
        }
        int userIndex = ValidationUtils.readIntInRange("Choose user: ", 1, users.length) - 1;
        currentUser = users[userIndex];
        System.out.println("Logged in as: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
        currentUser.displayPermissions();
    }

    // -------------------- PERMISSION HELPERS --------------------
    private boolean isAdmin() {
        return currentUser instanceof AdminUser;
    }

    private boolean isRegular() {
        return currentUser instanceof RegularUser;
    }

    // -------------------- MAIN MENU DISPLAY --------------------
    private void displayMainMenu() {
        System.out.println("\n===================================");
        System.out.println("   PROJECT/TASK MANAGEMENT SYSTEM");
        System.out.println("===================================");
        System.out.println("1. Manage Projects");
        System.out.println("2. Manage Tasks");
        System.out.println("3. Manage Users");
        System.out.println("4. View Reports");
        System.out.println("5. Browse Projects");
        System.out.println("6. Status Reports");
        System.out.println("7. Exit");
    }

    // -------------------- PROJECT MANAGEMENT --------------------
    private void manageProjects() {
        int choice;
        do {
            System.out.println("\n--- Manage Projects ---");
            System.out.println("1. Add Project");
            System.out.println("2. View All Projects");
            System.out.println("3. Filter Projects by Type");
            System.out.println("4. Search Projects by Budget Range");
            if (isAdmin()) {
                System.out.println("5. Delete Project");
                System.out.println("6. Back to Main Menu");
            } else {
                System.out.println("5. Back to Main Menu");
            }
            int maxOption = isAdmin() ? 6 : 5;
            choice = ValidationUtils.readIntInRange("Choose: ", 1, maxOption);
            switch (choice) {
                case 1 -> addProject();
                case 2 -> viewAllProjects();
                case 3 -> filterProjectsByType();
                case 4 -> searchByBudgetRange();
                case 5 -> {
                    if (isAdmin()) {
                        deleteProject();
                    } else {
                        return;
                    }
                }
                case 6 -> { if (isAdmin()) return; }
            }
        } while (true);
    }

    private void addProject() {
        System.out.println("\n--- Add Project ---");
        System.out.println("1. Software Project");
        System.out.println("2. Hardware Project");
        int type = ValidationUtils.readIntInRange("Select type: ", 1, 2);
        String name = ValidationUtils.readNonEmptyString("Project Name: ");
        String desc = ValidationUtils.readNonEmptyString("Description: ");
        double budget = ValidationUtils.readDouble("Budget: ");
        int teamSize = ValidationUtils.readInt("Team Size: ");

        Project project;
        if (type == 1) {
            String lang = ValidationUtils.readNonEmptyString("Programming Language: ");
            project = new SoftwareProject(name, desc, budget, teamSize, lang);
        } else {
            String hardware = ValidationUtils.readNonEmptyString("Hardware Type: ");
            project = new HardwareProject(name, desc, budget, teamSize, hardware);
        }
        projectService.addProject(project);
        System.out.println("Project created successfully! ID: " + project.getProjectId());
    }

    private void viewAllProjects() {
        Project[] projects = projectService.getAllProjects();
        if (projects.length == 0) {
            System.out.println("No projects found.");
            return;
        }
        System.out.println("\n--- All Projects ---");
        for (Project p : projects) {
            p.displayProject();
            System.out.println("-------------------");
        }
    }

    private void filterProjectsByType() {
        String type = ValidationUtils.readString("Enter type (Software/Hardware): ");
        Project[] filtered = projectService.filterByType(type);
        if (filtered.length == 0) {
            System.out.println("No projects of type " + type);
        } else {
            for (Project p : filtered) {
                p.displayProject();
                System.out.println("-------------------");
            }
        }
    }

    private void searchByBudgetRange() {
        double min = ValidationUtils.readDouble("Enter min budget: ");
        double max = ValidationUtils.readDouble("Enter max budget: ");
        if (min > max) {
            System.out.println("Min budget cannot be greater than max.");
            return;
        }
        Project[] results = projectService.filterByBudgetRange(min, max);
        if (results.length == 0) {
            System.out.println("No projects found in that budget range.");
        } else {
            for (Project p : results) {
                p.displayProject();
                System.out.println("-------------------");
            }
        }
    }

    // ** UPDATED: deleteProject with proper implementation **
    private void deleteProject() {
        if (!isAdmin()) {
            System.out.println("Only admins can delete projects.");
            return;
        }
        Project[] allProjects = projectService.getAllProjects();
        if (allProjects.length == 0) {
            System.out.println("No projects to delete.");
            return;
        }
        String projectId = ValidationUtils.readNonEmptyString("Enter Project ID to delete: ");
        boolean deleted = projectService.deleteProject(projectId);
        if (deleted) {
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Project not found. Available projects:");
            for (Project p : allProjects) {
                System.out.println(p.getProjectId() + " - " + p.getName());
            }
        }
    }

    // -------------------- TASK MANAGEMENT --------------------
    private void manageTasks() {
        int choice;
        do {
            System.out.println("\n--- Manage Tasks ---");
            System.out.println("1. Add Task to Project");
            System.out.println("2. View Tasks for Project");
            if (isAdmin()) {
                System.out.println("3. Update Task Status");
                System.out.println("4. Delete Task");
                System.out.println("5. Back to Main Menu");
            } else {
                System.out.println("3. Back to Main Menu");
            }
            int maxOption = isAdmin() ? 5 : 3;
            choice = ValidationUtils.readIntInRange("Choose: ", 1, maxOption);
            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> {
                    if (isAdmin()) {
                        updateTaskStatus();
                    } else {
                        return;
                    }
                }
                case 4 -> { if (isAdmin()) deleteTask(); }
                case 5 -> { if (isAdmin()) return; }
            }
        } while (true);
    }


    private void addTask() {
        // First check if there are any projects
        Project[] allProjects = projectService.getAllProjects();
        if (allProjects.length == 0) {
            System.out.println("No projects exist yet. Please create a project first.");
            return;
        }

        String projectId = ValidationUtils.readNonEmptyString("Enter Project ID: ");
        Project project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found. Available projects:");
            for (Project p : allProjects) {
                System.out.println(p.getProjectId() + " - " + p.getName());
            }
            return;
        }
        String taskName = ValidationUtils.readNonEmptyString("Task Name: ");

        for (Task t : project.getTasks()) {
            if (t.getName().equalsIgnoreCase(taskName)) {
                System.out.println("Task with that name already exists.");
                return;
            }
        }
        System.out.println("Select status: 1. Pending  2. In Progress  3. Completed");
        int statusChoice = ValidationUtils.readIntInRange("Status: ", 1, 3);
        TaskStatus status = switch (statusChoice) {
            case 1 -> TaskStatus.PENDING;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.COMPLETED;
            default -> TaskStatus.PENDING;
        };
        // Optional assign user
        System.out.print("Assign to user ID (or leave blank): ");
        String userId = ValidationUtils.readString("");  // allow empty
        if (!userId.isEmpty() && userService.findUserById(userId) == null) {
            System.out.println("User not found. Task will be unassigned.");
            userId = null;
        }
        boolean added = taskService.addTaskToProject(projectId, taskName, status, userId);
        if (added) {
            System.out.println("Task added successfully.");
        } else {
            System.out.println("Failed to add task.");
        }
    }

    private void viewTasks() {
        String projectId = ValidationUtils.readNonEmptyString("Enter Project ID: ");
        Project project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        Task[] tasks = project.getTasks();
        if (tasks.length == 0) {
            System.out.println("No tasks for this project.");
            return;
        }
        System.out.println("\n--- Tasks for Project: " + project.getName() + " ---");
        for (Task t : tasks) {
            System.out.printf("ID: %s | Name: %s | Status: %s | Assigned User: %s\n",
                    t.getTaskId(), t.getName(), t.getStatus(),
                    t.getAssignedUserId() != null ? t.getAssignedUserId() : "None");
        }
    }

    private void updateTaskStatus() {
        if (!isAdmin()) {
            System.out.println("Only admins can update task status.");
            return;
        }
        String projectId = ValidationUtils.readNonEmptyString("Enter Project ID: ");
        Project project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        String taskId = ValidationUtils.readNonEmptyString("Enter Task ID: ");
        Task task = project.findTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.println("Current status: " + task.getStatus());
        System.out.println("Select new status: 1. Pending  2. In Progress  3. Completed");
        int statusChoice = ValidationUtils.readIntInRange("New status: ", 1, 3);
        TaskStatus newStatus = switch (statusChoice) {
            case 1 -> TaskStatus.PENDING;
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.COMPLETED;
            default -> task.getStatus();
        };
        boolean updated = taskService.updateTaskStatus(projectId, taskId, newStatus);
        if (updated) {
            System.out.println("Task status updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private void deleteTask() {
        if (!isAdmin()) {
            System.out.println("Only admins can delete tasks.");
            return;
        }
        String projectId = ValidationUtils.readNonEmptyString("Enter Project ID: ");
        Project project = projectService.findProjectById(projectId);
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        String taskId = ValidationUtils.readNonEmptyString("Enter Task ID: ");
        boolean deleted = taskService.deleteTask(projectId, taskId);
        if (deleted) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    // -------------------- USER MANAGEMENT --------------------
    private void manageUsers() {
        int choice;
        do {
            System.out.println("\n--- Manage Users ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Back to Main Menu");
            choice = ValidationUtils.readIntInRange("Choose: ", 1, 3);
            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewAllUsers();
            }
        } while (choice != 3); // while loop
    }

    private void addUser() {
        System.out.println("Select role: 1. Regular  2. Admin");
        int role = ValidationUtils.readIntInRange("Role: ", 1, 2);
        String name = ValidationUtils.readNonEmptyString("Name: ");
        String email = ValidationUtils.readNonEmptyString("Email: ");
        User user;
        if (role == 1) {
            user = new RegularUser(name, email);
        } else {
            user = new AdminUser(name, email);
        }
        userService.addUser(user);
        System.out.println("User created! ID: " + user.getUserId());
    }

    private void viewAllUsers() {
        User[] users = userService.getAllUsers();
        if (users.length == 0) {
            System.out.println("No users.");
            return;
        }
        System.out.println("\n--- All Users ---");
        for (User u : users) {
            System.out.printf("ID: %s | Name: %s | Email: %s | Role: %s\n",
                    u.getUserId(), u.getName(), u.getEmail(), u.getRole());
            u.displayPermissions();
        }
    }

    // -------------------- REPORTS --------------------
    private void viewReports() {
        int choice;
        do {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Project Completion Report");
            System.out.println("2. All Projects Summary");
            System.out.println("3. Back to Main Menu");
            choice = ValidationUtils.readIntInRange("Choose: ", 1, 3);
            switch (choice) {
                case 1 -> {
                    String projectId = ValidationUtils.readNonEmptyString("Enter Project ID: ");
                    reportService.displayProjectCompletion(projectId);
                }
                case 2 -> reportService.displayAllProjectsSummary();
            }
        } while (choice != 3);
    }

    private void browseProjects() {
        viewAllProjects();
    }

    private void statusReports() {
        reportService.displayAllProjectsSummary();
    }
}