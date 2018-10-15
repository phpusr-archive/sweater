package com.example.sweater.domain

import com.example.sweater.domain.dto.MessageDto
import org.hibernate.validator.constraints.Length
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
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
) {
        @field:ManyToMany
        @JoinTable(
                name = "message_likes",
                joinColumns = [JoinColumn(name = "message_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id")]
        )
        val likes = mutableSetOf<User>()
}

interface MessageRepo : CrudRepository<Message, Long> {
        @Query("select new com.example.sweater.domain.dto.MessageDto(" +
                "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0" +
                ") " +
                "from Message m left join m.likes ml " +
                "group by m")
        fun findAll(pageable: Pageable, @Param("user") user: User): Page<MessageDto>

        @Query("select new com.example.sweater.domain.dto.MessageDto(" +
                "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0" +
                ") " +
                "from Message m left join m.likes ml " +
                "where m.tag = :tag " +
                "group by m")
        fun findByTag(@Param("tag") tag: String, pageable: Pageable, @Param("user") user: User): Page<MessageDto>

        @Query("select new com.example.sweater.domain.dto.MessageDto(" +
                "m, count(ml), sum(case when ml = :user then 1 else 0 end) > 0" +
                ") " +
                "from Message m left join m.likes ml " +
                "where m.author = :author " +
                "group by m")
        fun findByUser(pageable: Pageable, @Param("author") author: User, user: User): Page<MessageDto>
}
