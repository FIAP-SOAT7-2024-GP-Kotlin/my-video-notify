package soat7.group61.myvideonotify.business.service

import com.mailersend.sdk.MailerSend
import com.mailersend.sdk.MailerSendResponse
import com.mailersend.sdk.emails.Email
import com.mailersend.sdk.exceptions.MailerSendException
import mu.KLogging
import org.springframework.stereotype.Service
import soat7.group61.myvideonotify.config.MailerSendProperties

@Service
class NotifyService(
    private val properties: MailerSendProperties
) {

    private companion object : KLogging()

    private val mailerSend: MailerSend = MailerSend()

    init {
        mailerSend.token = properties.apiKey
    }

    suspend fun sendEmail(email: Email) = try {
        val ms = MailerSend()
        email.setFrom(properties.fromName, properties.fromEmail)
        logger.info { "Sending email notification: ${email.subject}" }

        val response: MailerSendResponse = ms.emails().send(email)
        logger.info { "Response Notification: ${response.messageId}" }
    } catch (e: MailerSendException) {
        e.printStackTrace()
        logger.error { "Response Notification Error: ${e.message}" }
    }
}
