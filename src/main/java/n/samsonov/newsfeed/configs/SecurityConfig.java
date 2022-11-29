package n.samsonov.newsfeed.configs;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.security.AuthEntryPoint;
import n.samsonov.newsfeed.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private  final AuthEntryPoint authEntryPoint;

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/*", "/api/v1/file/*").permitAll()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/user/*").authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint);


        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
}

