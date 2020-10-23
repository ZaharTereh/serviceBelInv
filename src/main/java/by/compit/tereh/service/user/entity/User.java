package by.compit.tereh.service.user.entity;

import lombok.Data;

import java.util.Set;

@Data
public class User {

    private String name;
    private String description;
    private Set<Group> groups;

    public User() {
    }

    public User(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public User(String name, String description, Set<Group> groups) {
        this.name = name;
        this.description = description;
        this.groups = groups;
    }

}
