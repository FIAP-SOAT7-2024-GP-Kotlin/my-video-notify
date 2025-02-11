package soat7.group61.myvideonotify.thirdparty

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import soat7.group61.myvideonotify.thirdparty.api.GetUserByIdResponse
import soat7.group61.myvideonotify.thirdparty.api.GetUserRequest
import soat7.group61.myvideonotify.thirdparty.common.HttpClient
import soat7.group61.myvideonotify.thirdparty.config.MyVideoFunctionConfig

@Component
class MyVideoFunctionClient(
    val config: MyVideoFunctionConfig,
    logger: KLogger = KotlinLogging.logger { MyVideoFunctionClient::class.java },
    webClientBuilder: WebClient.Builder,
    objectMapper: ObjectMapper
) : HttpClient(config, objectMapper, logger, webClientBuilder) {

    private companion object : KLogging()

    suspend fun findUser(request: GetUserRequest): GetUserByIdResponse {
        return webClient.post()
            .uri(config.url)
            .header("Authorization", config.accessToken)
            .bodyValue(request)
            .awaitExchange { response ->
                when (response.statusCode()) {
                    HttpStatus.OK -> validateResponse(response.awaitBody<GetUserByIdResponse>())
                    else -> throw Exception()
                }
            }
    }

    private fun validateResponse(data: GetUserByIdResponse) =
        if (!HttpStatus.valueOf(data.statusCode).is2xxSuccessful) {
            throw Exception("Invalid response")
        } else {
            logger.info { "User found" }
            data
        }
}
