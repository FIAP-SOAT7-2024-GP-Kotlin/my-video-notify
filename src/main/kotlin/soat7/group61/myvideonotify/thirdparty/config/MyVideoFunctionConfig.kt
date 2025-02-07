package soat7.group61.myvideonotify.thirdparty.config

import org.springframework.boot.context.properties.ConfigurationProperties
import soat7.group61.myvideonotify.thirdparty.common.HttpConnectionConfig

@ConfigurationProperties(prefix = "third-party.my-video-function")
class MyVideoFunctionConfig(
    override val url: String,
    val accessToken: String,
    override val connectTimeout: Int,
    override val connectionReadTimeout: Long,
    override val connectionWriteTimeout: Long
) : HttpConnectionConfig
