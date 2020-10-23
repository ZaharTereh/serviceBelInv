package by.compit.tereh.service.user.entity;

import org.springframework.security.core.GrantedAuthority;

@lombok.Getter
public class Group implements GrantedAuthority {

    private String name;

    private String description;

    public Group(String name) {
        this.name = name;
    }


    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
