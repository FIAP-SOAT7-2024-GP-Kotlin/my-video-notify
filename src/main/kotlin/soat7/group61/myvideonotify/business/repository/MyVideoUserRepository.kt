package soat7.group61.myvideonotify.business.repository

import soat7.group61.myvideonotify.business.model.UserData
import java.util.UUID

interface MyVideoUserRepository {

    suspend fun findUserById(userId: UUID): UserData
}
