package in.satish.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource datasource;
	
	//Authentication
	@Autowired
	public void authManager(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		    .dataSource(datasource)
		    .passwordEncoder(new BCryptPasswordEncoder())
		    .usersByUsernameQuery("select username,password,enabled from users where username=?")
		    .authoritiesByUsernameQuery("select username,authority from authorities where username=?");
	}
	
	//Authorization
	@Bean
   public SecurityFilterChain security(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests((req) ->
               req.requestMatchers("/welcome").permitAll()
                       .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                       .requestMatchers("/admin").hasAnyRole("ADMIN")
                       .anyRequest().authenticated()
       ).formLogin(withDefaults());
	   
	   return http.build();
   }
}
