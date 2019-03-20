package ru.education.repo;

public enum EducationLevel {

    PRESCHOOL(0, "Дошкольное образование"),
    PRIMARY(1, "Начальное общее образование"),
    BASIC(2, "Основное общее образование"),
    SECONDARY(3, "Среднее общее образование"),
    SECONDARY_PROF(4, "Среднее профессиональное образование"),
    HIGH_BACHELOR(5, "Высшее образование — бакалавриат"),
    HIGH_MAGISTRACY(6, "Высшее образование — специалитет, магистратура"),
    HIGH_QUALIFICATION(7, "Высшее образование — подготовка кадров высшей квалификации");

    private short code;
    private String name;

    EducationLevel(int code, String name) {
        this.code = (short) code;
        this.name = name;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Short codeFromEducationLevel(EducationLevel value) {
        for (EducationLevel type : values()) {
            if (type == value) {
                return type.getCode();
            }
        }
        return null;
    }

    public static EducationLevel educationLevelFromCode(short code) {
        for (EducationLevel type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
