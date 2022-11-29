package n.samsonov.newsfeed.errors;

public interface ValidationConstants {

    String USERNAME_SIZE_NOT_VALID = "Username size wrong. Expected between 3 and 25";
    String NEWS_DESCRIPTION_SIZE_NOT_VALID = "News description size wrong. Expected between 3 and 130";
    String NEWS_DESCRIPTION_HAS_TO_BE_PRESENT = "News description shouldn't be null";
    String ID_MUST_BE_POSITIVE = "ID must be positive";
    String REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT = "Parameter page shouldn't be null";
    String REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT = "Parameter perPage shouldn't be null";
    String TAGS_NOT_VALID = "Tags not valid";
    String NEWS_IMAGE_HAS_TO_BE_PRESENT = "Image mustn't be null";
    String USER_WITH_THIS_EMAIL_ALREADY_EXIST = "user with this email is already registered";
    String USER_NAME_HAS_TO_BE_PRESENT = "User name has to be present";
    String TASK_NOT_FOUND = "Task not found";
    String TASK_PATCH_UPDATED_NOT_CORRECT_COUNT = "Task patch updated has incorrect count";
    String TASKS_PAGE_GREATER_OR_EQUAL_1 = "Tasks page greater or equal 1";
    String TASKS_PER_PAGE_GREATER_OR_EQUAL_1 = "Tasks per page greater or equal 1";
    String TASKS_PER_PAGE_LESS_OR_EQUAL_100 = "Tasks per page less or equal 100";

    String ROLE_SIZE_NOT_VALID = "role size invalid";
    String EMAIL_SIZE_NOT_VALID = "email size invalid";
    String MUST_NOT_BE_NULL = "shouldn't be null";
    String USER_NOT_FOUND = "Couldn't not find user";
    String TOKEN_NOT_PROVIDED = "JWT token not provided";
    String UNAUTHORISED = "unauthorised";
    String USER_EMAIL_NOT_NULL = "user email shouldn't be null";
    String USER_PASSWORD_NULL = "user password should be null";
    String USER_ROLE_NOT_NULL = "user role shouldn't be null";
    String NEWS_TITLE_SIZE = "news title size  invalid";
    String NEWS_TITLE_NOT_NULL = "title has to be present";
    String PARAM_PAGE_NOT_NULL = "Required Integer parameter 'page' is not present";
    String PARAM_PER_PAGE_NOT_NULL = "Required Integer parameter 'perPage' is not present";
    String USER_EMAIL_NOT_VALID = "user email invalid";
    String PAGE_SIZE_NOT_VALID = "news page must be greater or equal 1";
    String PER_PAGE_MIN_NOT_VALID = "perPage must be greater or equal 1";
    String PER_PAGE_MAX_NOT_VALID = "perPage must be less or equal 100";
    String CODE_NOT_NULL = "Required String parameter 'code' is not present";
    String EXCEPTION_HANDLER_NOT_PROVIDED = "Exception handler not provided";
    String REQUEST_IS_NOT_MULTIPART = "Current request isn't a multipart request";
    String MAX_UPLOAD_SIZE_EXCEEDED = "Max upload size exceeded";
    String USER_AVATAR_NOT_NULL = "user avatar shouldn't be null";
    String PASSWORD_NOT_VALID = "password invalid";
    String PASSWORD_NOT_NULL = "user password shouldn't be null";
    String NEWS_NOT_FOUND = "news not found";
    String USER_ALREADY_EXISTS = "User already exists";
    String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "Invalid HTTP request";
    String UNKNOWN = "unknown";
}
