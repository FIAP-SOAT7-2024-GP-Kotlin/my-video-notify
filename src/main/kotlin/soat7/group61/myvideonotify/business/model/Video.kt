package soat7.group61.myvideonotify.business.model

import soat7.group61.myvideonotify.business.enum.VideoStatus
import java.time.Instant
import java.util.UUID

data class Video(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val status: VideoStatus,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val metadata: Map<String, Any>? = emptyMap(),
    val inputPath: String,
    val outputPath: String? = null
)
