package com.authserver.concepts.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private UserDetailsService userDetailsService;
//	@Autowired
//	private DaoAuthenticationProvider authProvider;
	
	@Bean
	protected AuthenticationManager getAuthenticationManager() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/oauth/**").permitAll().and()
		.addFilterBefore(new SimpleCorsFilter(), ChannelProcessingFilter.class);
	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers(HttpMethod.POST, "/auth/verify/**/lastlogin");
//	}
	
	@Bean
	PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
//	}
//	
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//		authProvider = new DaoAuthenticationProvider();
//		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//		authProvider.setUserDetailsService(userService);
//		return authProvider;
//	}

}
