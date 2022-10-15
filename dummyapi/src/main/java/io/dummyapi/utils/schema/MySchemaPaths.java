package io.dummyapi.utils.schema;

public enum MySchemaPaths {

    SEARCH_USERS_SCHEMA(
            System.getProperty("user.dir") + "\\src\\test\\resources\\schemas\\user\\search\\search-users-schema.json"
    ),
    SEARCH_USER_SCHEMA(
            System.getProperty("user.dir") + "\\src\\test\\resources\\schemas\\user\\search\\search-user-schema.json"
    ),
    CREATE_USER_SCHEMA(
            System.getProperty("user.dir") + "\\src\\test\\resources\\schemas\\user\\create\\create-user-schema.json"
    ),
    DELETE_USER_SCHEMA(
            System.getProperty("user.dir") + "\\src\\test\\resources\\schemas\\user\\delete\\delete-user-schema.json"
    ),
    PUT_USER_SCHEMA(
            System.getProperty("user.dir") + "\\src\\test\\resources\\schemas\\user\\put\\put-user-schema.json"
    );

    private final String value;

    MySchemaPaths(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
