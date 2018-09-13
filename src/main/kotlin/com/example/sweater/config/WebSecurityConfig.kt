package com.example.sweater.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Qualifier("dataSource")
    @Autowired
    private lateinit var dataSource: DataSource

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
    }

    /*@Bean
    public override fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
                .username("u")
                .password("1")
                .roles("USER")
                .build()

        return InMemoryUserDetailsManager(user)
    }*/

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from user where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from user u inner join user_role ur on u.id = ur.user_id where u.username = ?")
    }
}