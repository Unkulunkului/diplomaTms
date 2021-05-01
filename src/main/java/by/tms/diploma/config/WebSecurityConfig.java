package by.tms.diploma.config;

import by.tms.diploma.listener.SessionListener;
import by.tms.diploma.service.impl.CustomUserDetailsService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = "by.tms.diploma")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/registration", "/user/authentication", "/hotel", "/tour").anonymous()
                .antMatchers("/hotel/add", "/tour/add", "/tour/edit", "/hotel/edit", "/tour/delete",
                        "/request/fromClient/**").hasAnyAuthority("MODERATOR", "ADMIN")
//                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/user/authentication")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/user/authentication")
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/image/**");
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> listenerSessionBean(){
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "unkulunkului",
                "api_key", "186826351968634",
                "api_secret", "rsF8_0V8T2vcFHuCYZnrOlkwlIY"));
    }
}
