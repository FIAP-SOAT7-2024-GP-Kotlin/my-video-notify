package soat7.group61.myvideonotify.messaging.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class NatsProperties(
    @Value("\${messaging.group}") val group: String,
    @Value("\${nats.spring.server}") val natsServerUrl: String
)
