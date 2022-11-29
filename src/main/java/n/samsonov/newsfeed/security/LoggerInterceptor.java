package n.samsonov.newsfeed.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.entity.LogsEntity;
import n.samsonov.newsfeed.repository.LogsRepository;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoggerInterceptor implements HandlerInterceptor {

    private final LogsRepository logsRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws Exception {

        LogsEntity log = new LogsEntity();
        log.setMethod(request.getMethod());
        log.setStatusCode(response.getStatus());
        logsRepository.save(log);
    }
}
