package com.example.sweater.domain

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    val username: String,
    val password: String,
    var active: Boolean = false,
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<Role> = mutableSetOf()
)

interface UserRepo : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}