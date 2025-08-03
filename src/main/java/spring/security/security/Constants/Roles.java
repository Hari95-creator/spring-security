package spring.security.security.Constants;

import java.util.Set;

public enum Roles {

    ADMIN(Set.of(Permission.READ_USER, Permission.WRITE_USER, Permission.DELETE_USER, Permission.VIEW_REPORT)),
    USER(Set.of(Permission.READ_USER));

    private final Set<Permission> permissions;

    Roles(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
