package com.trademarket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.trademarket.security.RestAuthenticationEntryPoint;




@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {

	@Configuration

	@Order(1)
	public static class FormLoginWebSecurityConfigurerAdapter extends
			WebSecurityConfigurerAdapter {

		@Autowired
		private RestAuthenticationEntryPoint authenticationEntryPoint;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.authorizeRequests()
					.antMatchers("/**").permitAll()
					.antMatchers("/dashboard/**").hasRole("ADMIN")
					.antMatchers("/enroll/**").hasAnyRole("USER", "ADMIN")
					.anyRequest().authenticated()
					.and()
				    .formLogin().loginPage("/login/layout").permitAll()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.and().httpBasic()
					.authenticationEntryPoint(authenticationEntryPoint).realmName("Protected API");
		}
		
		
	}

}
