package soat7.group61.my_notify.domain

import jakarta.persistence.*
import soat7.group61.my_notify.domain.enum.NotifyStatus
import soat7.group61.my_notify.domain.enum.NotifyType
import java.time.Instant
import java.util.UUID

@Entity
@Table(
    name = "notify",
    schema = "myvideo"
)
class NotifyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    val id: UUID,

    @Column(name = "email", length = 255, nullable = false)
    val email: String,

    @Column(name = "type", length = 255, nullable = false)
    val type: NotifyType,

    @Column(name = "message", length = 255, nullable = false)
    val message: String,

    @Column(name = "status", length = 255, nullable = false)
    val status: NotifyStatus,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant,

    )
