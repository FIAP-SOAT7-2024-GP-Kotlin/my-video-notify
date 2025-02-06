package soat7.group61.myvideonotify.domain.enum

enum class NotifyStatus {
    PENDING,
    SENT,
    FAILED,
    CANCELED,
    SUCCESS;

    companion object {
        fun fromString(value: String): NotifyStatus = when (value.lowercase()) {
            "pending" -> PENDING
            "sent" -> SENT
            "failed" -> FAILED
            "canceled" -> CANCELED
            "success" -> SUCCESS
            else -> throw IllegalArgumentException("Invalid NotifyStatus value: $value")
        }
    }

}
