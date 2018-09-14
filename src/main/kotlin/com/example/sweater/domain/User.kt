package com.example.sweater.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    private var username: String,
    private var password: String,

    var active: Boolean = false,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<Role> = mutableSetOf()
): UserDetails {

    override fun getUsername() = username
    fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword() = password

    override fun getAuthorities() = roles

    override fun isEnabled() = active

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    fun isAdmin() = roles.contains(Role.ADMIN)
}

interface UserRepo : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}