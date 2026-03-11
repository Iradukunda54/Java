package model;

public abstract class User {
    private static int idCounter = 0;
    private final String userId;
    private String name;
    private String email;
    private String role;

    public User(String name, String email, String role) {
        this.userId = generateId();
        this.name = name;
        this.email = email;
        this.role = role;
    }

    private String generateId() {
        idCounter++;
        return String.format("USR%03d", idCounter);
    }

    public abstract void displayPermissions();

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
}