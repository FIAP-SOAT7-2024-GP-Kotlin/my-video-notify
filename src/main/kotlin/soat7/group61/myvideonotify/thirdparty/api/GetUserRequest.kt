package soat7.group61.myvideonotify.thirdparty.api

data class GetUserRequest(
    val type: String,
    val email: String? = null,
    val id: String? = null
)
