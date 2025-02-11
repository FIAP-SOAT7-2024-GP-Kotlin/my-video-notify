package soat7.group61.myvideonotify.thirdparty

import org.springframework.stereotype.Component
import soat7.group61.myvideonotify.business.model.UserData
import soat7.group61.myvideonotify.business.repository.MyVideoUserRepository
import soat7.group61.myvideonotify.thirdparty.api.GetUserRequest
import java.util.UUID

@Component
class MyVideoUserGateway(
    private val client: MyVideoFunctionClient
) : MyVideoUserRepository {
    override suspend fun findUserById(userId: UUID): UserData {
        return UserData.from(client.findUser(GetUserRequest(type = "GET_USER_BY_ID", id = userId.toString())))
    }
}
