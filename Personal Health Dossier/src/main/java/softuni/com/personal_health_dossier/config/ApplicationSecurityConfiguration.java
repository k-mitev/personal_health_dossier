package softuni.com.personal_health_dossier.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import softuni.com.personal_health_dossier.service.impl.HealthDossierUserService;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final HealthDossierUserService healthDossierUserService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(HealthDossierUserService healthDossierUserService, PasswordEncoder passwordEncoder) {
        this.healthDossierUserService = healthDossierUserService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/", "/users/register", "/users/login").permitAll()
//                .antMatchers("/**").authenticated()
                .antMatchers("/home").authenticated()
                .antMatchers("/allergies/add", "/prescriptions/add",
                        "/immunizations/add", "/medical-centers/add", "/physicians/edit-profile/**",
                        "/modify/kilos/**", "/modify/height/**", "/modify/bloodGroup/**").hasRole("PHYSICIAN")
                .antMatchers("/allergies/all/**", "/prescriptions/all/**",
                        "/immunizations/all/**", "/medical-centers/all/**", "/patients/edit-profile/**",
                        "/modify/consentForOrgans/**").hasAnyRole("PATIENT", "ADMIN")
                .antMatchers("/physicians/add").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/home")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(healthDossierUserService)
                .passwordEncoder(passwordEncoder);

    }
}
