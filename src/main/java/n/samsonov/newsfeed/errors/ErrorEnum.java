package n.samsonov.newsfeed.errors;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    UNKNOWN(0, "unknown"),
    USERNAME_SIZE_NOT_VALID(1, ValidationConstants.USERNAME_SIZE_NOT_VALID),
    ROLE_SIZE_NOT_VALID(2, "role size invalid"),
    EMAIL_SIZE_NOT_VALID(3, "email size invalid"),
    MUST_NOT_BE_NULL(4, "must not be null"),
    USER_NOT_FOUND(5, "Could not find user"),
    TOKEN_NOT_PROVIDED(6, "JWT token not provided"),
    UNAUTHORISED(7, "unauthorised"),
    USER_EMAIL_NOT_NULL(8, "user email shouldn't be null"),
    USER_PASSWORD_NULL(9, "user password must be null"),
    USER_ROLE_NOT_NULL(10, ValidationConstants.USER_ROLE_NOT_NULL),
    NEWS_DESCRIPTION_SIZE(11, ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID),
    NEWS_DESCRIPTION_NOT_NULL(12, ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT),
    NEWS_TITLE_SIZE(13, ValidationConstants.NEWS_TITLE_SIZE),
    NEWS_TITLE_NOT_NULL(14, ValidationConstants.NEWS_TITLE_NOT_NULL),
    PARAM_PAGE_NOT_NULL(15, "Required Integer parameter 'page' is not present"),
    PARAM_PER_PAGE_NOT_NULL(16, "Required Integer parameter 'perPage' is not present"),
    USER_EMAIL_NOT_VALID(17, "user email invalid"),
    PAGE_SIZE_NOT_VALID(18, "news page must be greater or equal 1"),
    PER_PAGE_MIN_NOT_VALID(19, "perPage must be greater or equal 1"),
    PER_PAGE_MAX_NOT_VALID(19, "perPage must be less or equal 100"),
    CODE_NOT_NULL(20, "Required String parameter 'code' is not present"),
    EXCEPTION_HANDLER_NOT_PROVIDED(21, "Exception handler not provided"),
    REQUEST_IS_NOT_MULTIPART(22, "Current request is not a multipart request"),
    MAX_UPLOAD_SIZE_EXCEEDED(23, ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED),
    USER_AVATAR_NOT_NULL(24, "user avatar shouldn't be null"),
    PASSWORD_NOT_VALID(25, "password invalid"),
    PASSWORD_NOT_NULL(26, "user password shouldn't be null"),
    NEWS_NOT_FOUND(27, "news not found"),
    ID_MUST_BE_POSITIVE(29, ValidationConstants.ID_MUST_BE_POSITIVE),
    USER_ALREADY_EXISTS(30,ValidationConstants.USER_ALREADY_EXISTS),
    TASK_NOT_FOUND(34, ValidationConstants.TASK_NOT_FOUND),
    TASK_PATCH_UPDATED_NOT_CORRECT_COUNT(35, ValidationConstants.TASK_PATCH_UPDATED_NOT_CORRECT_COUNT),
    TASKS_PAGE_GREATER_OR_EQUAL_1(37, ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1),
    TASKS_PER_PAGE_GREATER_OR_EQUAL_1(38, ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1),
    TASKS_PER_PAGE_LESS_OR_EQUAL_100(39, ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100),
    REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT(40, ValidationConstants.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT),
    REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT(41, ValidationConstants.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT),
    USER_NAME_HAS_TO_BE_PRESENT(43, ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT),
    TAGS_NOT_VALID(44, ValidationConstants.TAGS_NOT_VALID),
    NEWS_IMAGE_HAS_TO_BE_PRESENT(45, ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT),
    USER_WITH_THIS_EMAIL_ALREADY_EXIST(46, ValidationConstants.USER_WITH_THIS_EMAIL_ALREADY_EXIST),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(47, "Http request not valid");

    private final Integer code;
    private final String message;
    private static final Map<String, ErrorEnum> mapCode;

    static {
        mapCode = new HashMap<>();
        for (ErrorEnum v :ErrorEnum.values()) {
            mapCode.put(v.message, v);
        }
    }

    public static ErrorEnum getCodes(String message) {
        return mapCode.get(message);
    }
}
