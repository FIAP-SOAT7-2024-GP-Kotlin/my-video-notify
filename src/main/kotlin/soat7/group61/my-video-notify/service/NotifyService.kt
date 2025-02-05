package soat7.group61.`my-video-notify`.service

import com.mailersend.sdk.MailerSend
import com.mailersend.sdk.MailerSendResponse
import com.mailersend.sdk.exceptions.MailerSendException
import com.mailersend.sdk.emails.Email

fun sendEmail() {
    val email = Email()

    email.setFrom("name", "info@domain.com")
    email.addRecipient("name", "your@recipient.com")
    email.addRecipient("name 2", "jsantosrocha@gmail.com")

    email.setSubject("Email subject")

    email.setPlain("This is the text content")
    email.setHtml("This is the HTML content")

    val ms = MailerSend()
    ms.token = "Your API token"

    try {
        val response: MailerSendResponse = ms.emails().send(email)
        println(response.messageId)
    } catch (e: MailerSendException) {
        e.printStackTrace()
    }
}
