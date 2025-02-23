package soat7.group61.myvideonotify.fixture

import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.util.MultiValueMap
import soat7.group61.myvideonotify.business.enum.VideoStatus
import soat7.group61.myvideonotify.business.model.Video
import java.io.File
import java.time.Instant
import java.util.UUID

object VideoFixture {

    fun uploadVideoRequest(
        userId: UUID = UUID.randomUUID(),
        name: String = "test"
    ): MultiValueMap<String, HttpEntity<*>> {
        val file = File.createTempFile("test", ".mp4")

        val multipartBodyBuilder = MultipartBodyBuilder()
        multipartBodyBuilder.part("content", FileSystemResource(file))
            .header("Content-Disposition", "form-data; name=\"content\"; filename=\"test.mp4\"")

        multipartBodyBuilder.part("name", name)
        multipartBodyBuilder.part("user_id", userId.toString())

        file.deleteOnExit()

        return multipartBodyBuilder.build()
    }

    fun createVideo(): Video {
        return Video(
            id = UUID.randomUUID(),
            name = "test",
            userId = UUID.randomUUID(),
            status = VideoStatus.ERROR,
            outputPath = "outputPath",
            metadata = emptyMap(),
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            inputPath = "inputPath"
        )
    }
}
