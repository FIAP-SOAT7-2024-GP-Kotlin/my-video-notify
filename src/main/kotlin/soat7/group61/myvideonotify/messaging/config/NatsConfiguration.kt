package soat7.group61.myvideonotify.messaging.config

import io.nats.client.Connection
import io.nats.client.Nats
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfiguration(
    private val natsProperties: NatsProperties
) {

    companion object {
        private val log = KotlinLogging.logger { }
    }

    @Bean
    fun natsClient(): Connection {
        try {
            val connection = Nats.connect(natsProperties.natsServerUrl)
            log.info { "Connected to NATS server at ${natsProperties.natsServerUrl}" }
            return connection
        } catch (ex: Exception) {
            log.error { "Failed to connect to NATS server: ${ex.message}" }
            throw ex
        }
    }
}
