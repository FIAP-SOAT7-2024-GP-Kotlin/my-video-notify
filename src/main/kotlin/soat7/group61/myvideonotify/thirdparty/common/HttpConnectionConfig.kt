package soat7.group61.myvideonotify.thirdparty.common

interface HttpConnectionConfig {
    val url: String
    val connectTimeout: Int
    val connectionReadTimeout: Long
    val connectionWriteTimeout: Long
}
