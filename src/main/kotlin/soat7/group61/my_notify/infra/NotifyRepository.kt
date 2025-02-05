package soat7.group61.my_notify.infra

import org.springframework.data.jpa.repository.JpaRepository
import soat7.group61.my_notify.domain.NotifyEntity
import java.util.UUID

interface NotifyRepository: JpaRepository<NotifyEntity, UUID> {
    fun findByEmail(email: String): List<NotifyEntity>
    fun findByType(type: String): List<NotifyEntity>
}