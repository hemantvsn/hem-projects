package com.hemant.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * All the resources which are not explicitly ignored 
 * or not part of Authorization server are protected by Resource server
 * @author hemant
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/**
	 * 1. public/** APIs can be accessed by any user, authenticated or not
	 * 2. admin/** APIs can be accessed by authenticated users who possess the role ADMIN
	 * 3. Remaining all APIs can only be accessed by authenticated users, irrespective of their roles
	 */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/public/**").anonymous();
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.logout().disable();
    }
}
