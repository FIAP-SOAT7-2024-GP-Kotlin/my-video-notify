package soat7.group61.myvideonotify.business.handler

import com.mailersend.sdk.emails.Email
import mu.KLogging
import org.springframework.stereotype.Component
import soat7.group61.myvideonotify.business.model.Video
import soat7.group61.myvideonotify.business.service.NotifyService

@Component
class VideoErrorHandler(
    private val notifyService: NotifyService
) {
    private companion object : KLogging()

    suspend fun handle(video: Video){
        logger.info { "Handling video error status: ${video.id}" }

        notifyService.sendEmail(buildEmail(video))
    }

    private suspend fun buildEmail(video: Video) :Email{
        val email = Email()

        //TODO adicionar chamada para o auth e trazer o user

        email.addRecipient("user", "user@email.com")
        email.setSubject("Fail to process [" + video.name + "]")
        email.setPlain("The process of " + video.name + " must be failed!")

        return email
    }
}
