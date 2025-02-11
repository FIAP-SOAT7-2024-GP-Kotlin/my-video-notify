package soat7.group61.myvideonotify.messaging.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.nats.client.Connection
import io.nats.client.Message
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import soat7.group61.myvideonotify.business.handler.VideoCompletedHandler
import soat7.group61.myvideonotify.business.model.Video

@Component
class VideoCompletedConsumer(
    @Value("\${messaging.topics.video-completed}") private val topic: String,
    @Value("\${messaging.group}") private val group: String,
    private val natsConnection: Connection,
    private val objectMapper: ObjectMapper,
    private val videoCompletedHandler: VideoCompletedHandler
) {

    private companion object : KLogging()

    @PostConstruct
    fun consume() {
        logger.info { "Connected url ${natsConnection.connectedUrl}" }
        natsConnection.createDispatcher(::handle)
            .subscribe(topic, group)
    }

    private fun handle(message: Message) {
        val video = objectMapper.readValue<Video>(message.data)
        runBlocking { videoCompletedHandler.handle(video) }
    }
}
