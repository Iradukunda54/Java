package model;

public class SoftwareProject extends Project {
    private String programmingLanguage;

    public SoftwareProject(String name, String description, double budget, int teamSize, String programmingLanguage) {
        super(name, description, budget, teamSize);
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String getProjectDetails() {
        return "Software Project: " + getName() + " | Language: " + programmingLanguage;
    }


    @Override
    public String getProjectType() {
        return "Software";
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}