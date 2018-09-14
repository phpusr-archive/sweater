package com.example.sweater.domain

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

@Entity
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val text: String,
        val tag: String,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        val author: User
)

interface MessageRepo : CrudRepository<Message, Int> {
        fun findByTag(tag: String): List<Message>
}
