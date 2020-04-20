package net.thumbtack.bank.config;

import net.thumbtack.bank.jwt.JwtAuthenticationProvider;
import net.thumbtack.bank.jwt.JwtTokenService;
import net.thumbtack.bank.jwt.filtres.CookieJwtAuthFilter;
import net.thumbtack.bank.jwt.filtres.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenService jwtTokenService;

    public WebSecurityConfig(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().disable();
        http.requestCache().disable();
        http.anonymous();


        RequestMatcher loginPageMatcher = new AntPathRequestMatcher("/signin");
        RequestMatcher notLoginPageMatcher = new NegatedRequestMatcher(loginPageMatcher);

        JwtAuthFilter authFilter = new CookieJwtAuthFilter(notLoginPageMatcher);

        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);

        http
                .authorizeRequests().antMatchers("/signup", "/signin", "/sgnIn", "/sgnUp").permitAll()
                .and()
                .authorizeRequests().antMatchers("/", "/ping").permitAll()
                .and()
                .authorizeRequests().antMatchers("/cards/create", "/cards/create-card",
                "/cards/put", "/cards/put-money", "/cards/take-money", "/cards/information", "/cards/*/information").permitAll()
                .and()
                .authorizeRequests().antMatchers(
                "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**",
                "/css/**", "/img/**", "/js/**", "/*.ico").permitAll()
                .and()
                .authorizeRequests().anyRequest().hasAuthority("ROLE_ADMIN");
    }


    @Override
    public void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new JwtAuthenticationProvider(jwtTokenService));
    }

}
