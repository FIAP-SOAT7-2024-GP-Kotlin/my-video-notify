package soat7.group61.myvideonotify.business.service

import com.mailersend.sdk.MailerSend
import com.mailersend.sdk.MailerSendResponse
import com.mailersend.sdk.emails.Email
import com.mailersend.sdk.exceptions.MailerSendException
import soat7.group61.myvideonotify.business.model.Video

class NotifyService(
    private val email: Email
){
    suspend fun sendEmail(video: Video){
        email.setFrom("My-Video", "alssantos.482@gmail.com")
        email.addRecipient("name", video.userId.toString())

        email.setSubject("Fail to process [" + video.name + "]")

        email.setPlain("The process of" + video.name + "must be failed!")

        val ms = MailerSend()
        //ms.token = "Your API token"

        try {
            val response: MailerSendResponse = ms.emails().send(email)
            println(response.messageId)
        } catch (e: MailerSendException) {
            e.printStackTrace()
        }
    }
}
