package soat7.group61.myvideonotify.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import soat7.group61.myvideonotify.thirdparty.config.MyVideoFunctionConfig

@Configuration
@EnableConfigurationProperties(
    value = [MyVideoFunctionConfig::class]
)
class CustomConfigurationProperties
