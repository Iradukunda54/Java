# Task Management System

A console-based project/task management system built with Java 21.  
This application allows users to create projects, add tasks, assign users, track completion, and generate status reports.  
Data is stored in-memory using arrays.

## Features

- **Project Catalog Management**: Create, view, filter, and search projects by type or budget.
- **Task Operations**: Add, view, update, and delete tasks per project (role-based).
- **User Management**: Create regular and admin users; role-based permissions (admin can update/delete tasks).
- **Status Reporting**: Calculate completion percentages and display project progress.
- **Menu Navigation**: Interactive console with input validation.

## Prerequisites

- JDK 21
- IntelliJ IDEA (or any Java IDE)

## Setup

1. Clone the repository.
2. Open the project in IntelliJ.
3. Ensure the project SDK is set to JDK 21.
4. Run `Main.java`.

## Usage

1. On first run, you will be prompted to create an admin user.
2. Log in with that user.
3. Navigate through the main menu using numbers.
4. Follow submenus to perform operations.
5. All inputs are validated; invalid entries will prompt again.

## Menu Options

- **1. Add Project** – Create a new software or hardware project.
- **2. View All Projects** – Display all projects with details.
- **3. Add Task** – Add a task to a specific project.
- **4. View Tasks** – List all tasks of a project.
- **5. Update Task Status** – Change task status (Admin only).
- **6. Calculate Project Completion** – Show completion percentage for a project.
- **7. Delete Task** – Remove a task (Admin only).
- **8. Filter Projects by Type** – Show only software or hardware projects.
- **9. Search Projects by Budget** – Find projects within a budget range.
- **10. User Management** – Create or list users.
- **11. Logout / Switch User** – Change current user.
- **0. Exit** – Quit the application.

## User Stories Implemented

- US-1.1: Browse Projects
- US-1.2: Search Projects by Budget Range
- US-2.1: Add Task to Project
- US-2.2: Update Task Status
- US-3.1: Create User Profiles
- US-4.1: Calculate Project Completion Average
- US-5.1: Display Main Menu

## OOP Design

- **Encapsulation**: All fields are private with getters/setters.
- **Inheritance**: `Project` is abstract with `SoftwareProject` and `HardwareProject`; `User` is abstract with `RegularUser` and `AdminUser`.
- **Polymorphism**: Methods like `getProjectDetails()` are overridden.
- **Interfaces**: `Completable` ensures tasks can report completion.
- **Abstraction**: Abstract classes define contracts for projects and users.

## Testing Scenarios

The application has been tested against all scenarios in the PDF:

1. View Projects – Option 2
2. Add Software Project – Option 1
3. Add Hardware Project – Option 1
4. Add Task – Option 3
5. View Tasks – Option 4
6. Update Task Status – Option 5
7. Calculate Completion – Option 6
8. Invalid Inputs – Handled gracefully
9. ID Auto-generation – Verified

## Future Enhancements

- Replace arrays with Java Collections.
- Add file persistence.
- Implement user authentication.
- Enhance reporting dashboards.