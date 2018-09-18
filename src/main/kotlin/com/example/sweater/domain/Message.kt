package com.example.sweater.domain

import org.hibernate.validator.constraints.Length
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Message(
        @field:Id
        @field:GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long?,

        @field:NotBlank(message = "Please fill the message")
        @field:Length(max = 2048, message = "Message length > 2048")
        val text: String,

        @field:Length(max = 255, message = "Tag size > 255")
        val tag: String,

        var filename: String?,

        @field:ManyToOne(fetch = FetchType.EAGER)
        @field:JoinColumn(name = "user_id")
        var author: User?
)

interface MessageRepo : CrudRepository<Message, Long> {
        fun findAll(pageable: Pageable): Page<Message>
        fun findByTag(tag: String, pageable: Pageable): Page<Message>
}
