package soat7.group61.myvideonotify.thirdparty.config

import org.springframework.boot.context.properties.ConfigurationProperties
import soat7.group61.myvideonotify.thirdparty.common.HttpConnectionConfig

@ConfigurationProperties(prefix = "third-party.my-video-function")
class MyVideoFunctionConfig(
    override val url: String = "sss",
    val accessToken: String = "aa",
    override val connectTimeout: Int = 0,
    override val connectionReadTimeout: Long = 0,
    override val connectionWriteTimeout: Long = 0
) : HttpConnectionConfig
