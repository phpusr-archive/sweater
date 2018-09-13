package com.example.sweater.domain

import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val text: String,
        val tag: String
)

interface MessageRepo : CrudRepository<Message, Int> {
        fun findByTag(tag: String): List<Message>
}
