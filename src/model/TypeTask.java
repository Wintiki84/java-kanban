package model;

public enum TypeTask {
    TASK("TASK"),
    EPIC("EPIC"),
    SUB_TASK("SUB_TASK");

    private String value;

    TypeTask(String value) {
        this.value = value;
    }

    public static TypeTask fromString(String value) {
        if (value != null) {
            for (TypeTask typeTask : TypeTask.values()) {
                if (value.equalsIgnoreCase(typeTask.value)) {
                    return typeTask;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}
