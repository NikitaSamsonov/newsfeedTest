package n.samsonov.newsfeed.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
   private final ErrorEnum allErrors;
}
