package soat7.group61.myvideonotify.business.controller

import com.mailersend.sdk.emails.Email
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import soat7.group61.myvideonotify.business.service.NotifyService
import soat7.group61.myvideonotify.config.MailerSendProperties

@RestController
@RequestMapping("/notify")
class NotifyController(
    private val notifyService: NotifyService,
) {

    @PostMapping("/hello")
    suspend fun sayHello(): ResponseEntity<String> {
        val email = Email()

        email.addRecipient("André", "alssantos.482@gmail.com")
        email.setSubject("Fail to process [TESTE]")
        email.setPlain("The process of TESTE must be failed!")

        notifyService.sendEmail(email)

        return ResponseEntity.ok("Oi!")
    }
}
