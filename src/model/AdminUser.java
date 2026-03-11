package model;

public class AdminUser extends User {
    public AdminUser(String name, String email) {
        super(name, email, "Admin");
    }

    @Override
    public void displayPermissions() {
        System.out.println("Admin User: Full access (create, update, delete).");
    }
}