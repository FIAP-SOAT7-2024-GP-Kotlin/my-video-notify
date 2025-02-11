package soat7.group61.myvideonotify.thirdparty.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import mu.KLogger
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.netty.resources.LoopResources
import java.time.Duration
import java.util.concurrent.TimeUnit

abstract class HttpClient(
    config: HttpConnectionConfig,
    protected val objectMapper: ObjectMapper = LowerCamelCaseObjectMapper.instance,
    private val logger: KLogger,
    webClientBuilder: WebClient.Builder
) {

    companion object {
        val loopResources: LoopResources = LoopResources.create("reactor-webclient")
    }

    protected val webClient: WebClient = buildClient(config, objectMapper, logger, webClientBuilder)

    private fun buildClient(
        httpConnectionConfig: HttpConnectionConfig,
        objectMapper: ObjectMapper,
        logger: KLogger,
        builder: WebClient.Builder
    ): WebClient {
        val connectionProvider = ConnectionProvider
            .builder("third-party-cp")
            .lifo()
            .maxIdleTime(Duration.ofSeconds(5))
            .maxLifeTime(Duration.ofSeconds(60))
            .build()

        val httpClient = HttpClient.create(connectionProvider)
            .runOn(loopResources)
            .responseTimeout(Duration.ofMillis(httpConnectionConfig.connectionReadTimeout))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpConnectionConfig.connectTimeout)
            .doOnConnected {
                it
                    .addHandlerLast(
                        ReadTimeoutHandler(
                            httpConnectionConfig.connectionReadTimeout,
                            TimeUnit.MILLISECONDS
                        )
                    )
                    .addHandlerLast(
                        WriteTimeoutHandler(
                            httpConnectionConfig.connectionWriteTimeout,
                            TimeUnit.MILLISECONDS
                        )
                    )
            }

        val webClientBuilder = builder
            .baseUrl(httpConnectionConfig.url)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .codecs {
                it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
            }

        return webClientBuilder.build()
    }
}
