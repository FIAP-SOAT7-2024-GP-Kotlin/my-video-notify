package soat7.group61.myvideonotify.business.service

import com.mailersend.sdk.MailerSend
import com.mailersend.sdk.MailerSendResponse
import com.mailersend.sdk.emails.Email
import com.mailersend.sdk.exceptions.MailerSendException
import mu.KLogging
import org.springframework.stereotype.Service
import soat7.group61.myvideonotify.business.model.Video

@Service
class NotifyService(
    private val email: Email
){
    private companion object : KLogging()

    suspend fun sendEmail(email: Email) {
        logger.info { "Sending email notification: ${email.subject}" }

        email.setFrom("My-Video", "alssantos.482@gmail.com")

        val ms = MailerSend()

        try {
            val response: MailerSendResponse = ms.emails().send(email)
            logger.info { "Response Notification: ${response.messageId}" }
        } catch (e: MailerSendException) {
            e.printStackTrace()
            logger.error { "Response Notification Error: ${e.message}" }
        }
    }
}
