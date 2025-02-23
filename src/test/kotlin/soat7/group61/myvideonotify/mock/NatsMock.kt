package soat7.group61.myvideonotify.mock

import io.mockk.every
import io.mockk.mockk
import io.nats.client.Connection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Signal.subscribe

@Configuration
class NatsMock {

    @Bean
    fun natsClient() = mockedConnection()

    private fun mockedConnection(): Connection {
        return mockk(
            relaxed = true,
            relaxUnitFun = true
        ) {
            every { publish(any(), any()) } returns mockk()
            every { subscribe(any(), any()) } returns mockk()
            every { close() } returns mockk()
        }
    }
}
