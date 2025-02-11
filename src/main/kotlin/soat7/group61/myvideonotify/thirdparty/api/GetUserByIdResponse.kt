package soat7.group61.myvideonotify.thirdparty.api

data class GetUserByIdResponse(
    val message: String,
    val statusCode: Int,
    val body: User
) {
    data class User(
        val email: String,
        val role: String
    )
}
