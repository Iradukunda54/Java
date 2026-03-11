package model;

public class HardwareProject extends Project {
    private String hardwareType;

    public HardwareProject(String name, String description, double budget, int teamSize, String hardwareType) {
        super(name, description, budget, teamSize);
        this.hardwareType = hardwareType;
    }

    @Override
    public String getProjectDetails() {
        return "Hardware Project: " + getName() + " | Hardware: " + hardwareType;
    }

    // *** FIXED: now public ***
    @Override
    public String getProjectType() {
        return "Hardware";
    }

    public String getHardwareType() { return hardwareType; }
    public void setHardwareType(String hardwareType) { this.hardwareType = hardwareType; }
}