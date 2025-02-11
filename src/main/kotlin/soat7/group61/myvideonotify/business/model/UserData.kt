package soat7.group61.myvideonotify.business.model

import soat7.group61.myvideonotify.thirdparty.api.GetUserByIdResponse

data class UserData(
    val email: String
) {
    companion object {
        fun from(data: GetUserByIdResponse) = UserData(
            email = data.body.email
        )
    }
}
