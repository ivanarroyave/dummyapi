package io.dummyapi.utils.schema;


import static io.dummyapi.utils.so.AccordingToOperatingSystem.replaceLineSeparatorOfPath;
import static org.apache.commons.lang3.SystemUtils.USER_DIR;

public enum MySchemaPaths {

    SEARCH_USERS_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\user\\search\\search-users-schema.json"
    ),
    SEARCH_USER_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\user\\search\\search-user-schema.json"
    ),
    CREATE_USER_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\user\\create\\create-user-schema.json"
    ),
    DELETE_USER_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\user\\delete\\delete-user-schema.json"
    ),
    PUT_USER_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\user\\put\\put-user-schema.json"
    ),
    APP_ID_MISSING_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\app-id-missing-schema.json"
    ),
    APP_ID_NO_EXIST_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\app-id-no-exist-schema.json"
    ),
    PARAMS_NO_VALID_SCHEMA(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\params-no-valid-schema.json"
    ),
    BODY_NOT_VALID(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\body-not-valid-schema.json"
    ),
    RESOURCE_NOT_FOUND(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\resource-not-found-schema.json"
    ),
    PATH_NOT_FOUND(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\path-not-found-schema.json"
    ),
    SERVER_ERROR(
            USER_DIR + "\\src\\test\\resources\\schemas\\errortypes\\server-error-schema.json"
    );

    private final String value;

    MySchemaPaths(String value) {
        this.value = value;
    }

    public String getValue() {
        return replaceLineSeparatorOfPath(value, "\\");
    }
}
