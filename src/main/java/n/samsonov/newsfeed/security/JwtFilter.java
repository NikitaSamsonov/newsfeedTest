package n.samsonov.newsfeed.security;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.GenericFilterBean;

import static io.jsonwebtoken.lang.Strings.hasText;
import static java.nio.file.Paths.get;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl appUserDetailsService;

    private static ObjectMapper mapper = new ObjectMapper();

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return (bearer.substring(7));
        }
        return null;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Optional<String> token = Optional.ofNullable(getTokenFromRequest((HttpServletRequest) request));
            if (token.isPresent() && jwtProvider.validateToken(token.get())) {
                String id = jwtProvider.getIdFromToken(token.get());
                UserDetailsImpl userDetails  = appUserDetailsService.loadUserByUsername(id);
                var auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (CustomException e) {
            response
                    .getOutputStream()
                    .print(mapper
                            .writeValueAsString(CustomSuccessResponse
                                    .handleException(new Integer[]{ErrorEnum.TOKEN_NOT_PROVIDED.getCode()})));
        }
        chain.doFilter(request, response);
    }
}
