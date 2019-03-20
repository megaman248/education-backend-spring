package ru.education.rest.api.role;

public abstract class RoleCodeName {

    public static final String ROOT = "ROLE_ROOT";
    public static final String GUEST = "ROLE_GUEST";
    public static final String USER_CREATE = "ROLE_USER_CREATE";
    public static final String USER_READ = "ROLE_USER_READ";
    public static final String USER_READ_LIST = "ROLE_USER_READ_LIST";
    public static final String USER_UPDATE = "ROLE_USER_UPDATE";
    public static final String USER_DELETE = "ROLE_USER_DELETE";

    private RoleCodeName() {
    }
}
