package soat7.group61.myvideonotify.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class MailerSendProperties(
    @Value("\${mailersend.apikey}") val apiKey: String,
    @Value("\${mailersend.senderEmail}") val fromEmail: String,
    @Value("\${mailersend.senderName}") val fromName: String
)
