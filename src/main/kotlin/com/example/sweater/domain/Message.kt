package com.example.sweater.domain

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

@Entity
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val text: String,
        val tag: String,
        val filename: String?,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        val author: User
)

interface MessageRepo : CrudRepository<Message, Long> {
        fun findByTag(tag: String): List<Message>
}
