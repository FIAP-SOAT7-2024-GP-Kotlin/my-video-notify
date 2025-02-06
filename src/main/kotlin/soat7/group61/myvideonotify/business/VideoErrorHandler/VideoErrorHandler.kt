package soat7.group61.myvideonotify.business.VideoErrorHandler

import mu.KLogging
import org.springframework.stereotype.Component
import soat7.group61.myvideonotify.business.model.Video
import soat7.group61.myvideonotify.business.service.NotifyService

@Component
class VideoErrorHandler(
    private val notifyService: NotifyService
) {
    private companion object : KLogging()

    suspend fun handle(video: Video) {
        logger.info { "Handling video error status: ${video.id}" }

        notifyService.sendEmail(video)
    }
}
