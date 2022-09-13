package model;

public enum RequestMethod {
    GET("GET"),
    DELETE("DELETE"),
    POST("POST");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public static RequestMethod fromString(String value) {
        if (value != null) {
            for (RequestMethod requestMethod : RequestMethod.values()) {
                if (value.equalsIgnoreCase(requestMethod.value)) {
                    return requestMethod;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}

