package model;

public class RegularUser extends User {
    public RegularUser(String name, String email) {
        super(name, email, "Regular");
    }

    @Override
    public void displayPermissions() {
        System.out.println("Regular User: Can view and add tasks/projects.");
    }
}