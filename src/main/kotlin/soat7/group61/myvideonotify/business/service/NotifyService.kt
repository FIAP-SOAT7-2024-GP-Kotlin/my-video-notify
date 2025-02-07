package soat7.group61.myvideonotify.business.service

import com.mailersend.sdk.MailerSend
import com.mailersend.sdk.MailerSendResponse
import com.mailersend.sdk.emails.Email
import com.mailersend.sdk.exceptions.MailerSendException
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class NotifyService {

    private companion object : KLogging()

    suspend fun sendEmail(email: Email) = try {
        val ms = MailerSend()
        email.setFrom("My-Video", "fiapmyburguer@gmail.com")
        logger.info { "Sending email notification: ${email.subject}" }

        val response: MailerSendResponse = ms.emails().send(email)
        logger.info { "Response Notification: ${response.messageId}" }
    } catch (e: MailerSendException) {
        e.printStackTrace()
        logger.error { "Response Notification Error: ${e.message}" }
    }
}
