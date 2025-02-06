package soat7.group61.myvideonotify.domain.enum

enum class NotifyType() {
    EMAIL,
    SMS,
    PUSH,
    OTHER;

    companion object {
        fun fromString(value: String): NotifyType = when (value.lowercase()) {
            "email" -> EMAIL
            "sms" -> SMS
            "push" -> PUSH
            else -> OTHER
        }
    }
}
