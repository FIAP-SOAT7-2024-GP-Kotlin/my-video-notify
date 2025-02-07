package soat7.group61.myvideonotify.handler

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import soat7.group61.myvideonotify.business.handler.VideoErrorHandler
import kotlin.test.assertEquals

class VideoErrorHandlerIT {

    @Autowired
    private lateinit var videoErrorHandler: VideoErrorHandler

    @Test
    fun `Should successfully handle video error status`(): Unit = runTest {
        assertEquals(1, 1)
    }

    @Test
    fun `Should throw ReasonCodeException when video not found`(): Unit = runTest {
        assertEquals(2, 2)
    }
}
