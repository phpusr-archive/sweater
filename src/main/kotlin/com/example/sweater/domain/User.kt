package com.example.sweater.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
data class User(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @field:NotBlank(message = "Username can't be empy")
    private var username: String,

    @field:NotBlank(message = "Password can't be empy")
    private var password: String,

    var active: Boolean = false,
    var activationCode: String?,

    @field:Email(message = "Email is not correct")
    @field:NotBlank(message = "Password can't be empy")
    var email: String,

    @field:ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @field:CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @field:Enumerated(EnumType.STRING)
    val roles: MutableSet<Role> = mutableSetOf()
): UserDetails {

    override fun getUsername() = username
    fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword() = password
    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities() = roles

    override fun isEnabled() = active

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    fun isAdmin() = roles.contains(Role.ADMIN)
}

interface UserRepo : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByActivationCode(code: String): User?
}