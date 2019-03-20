package ru.education.rest.api.user.state;

public enum UserStateType {

    ACTIVE(0, "Активен."),
    LOCKED_PERIOD(1, "Заблокирован на период."),
    LOCKED_FROM(2, "Заблокирован с заданного времени."),
    LOCKED_IDLE(3, "Заблокирован, т.к. долго не было активности.");

    private final int code;
    private final String name;

    UserStateType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static UserStateType typeFromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserStateType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
