package soat7.group61.myvideonotify.business.handler

import com.mailersend.sdk.emails.Email
import mu.KLogging
import org.springframework.stereotype.Component
import soat7.group61.myvideonotify.business.model.Video
import soat7.group61.myvideonotify.business.repository.MyVideoUserRepository
import soat7.group61.myvideonotify.business.service.NotifyService

@Component
class VideoCompletedHandler(
    private val notifyService: NotifyService,
    private val myVideoUserRepository: MyVideoUserRepository
) {
    private companion object : KLogging()

    suspend fun handle(video: Video) {
        logger.info { "Handling video completed status: ${video.id}" }
        notifyService.sendEmail(buildEmail(video))
    }

    private suspend fun buildEmail(video: Video): Email {
        val user = myVideoUserRepository.findUserById(video.userId)
        return Email().apply {
            addRecipient("user", user.email)
            setSubject("Success to process [" + video.name + "]")
            setPlain("The file " + video.name + " was processed successfully!")
        }
    }
}
