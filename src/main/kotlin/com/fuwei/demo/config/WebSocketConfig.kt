package com.fuwei.demo.config

import com.fuwei.demo.interceptor.DefaultWebSocketInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import javax.annotation.Resource


@EnableWebSocket
@Configuration
class WebSocketConfig : WebSocketConfigurer {

    @Resource
    var defaultWebSocketHandler: WebSocketHandler? = null

    @Resource
    var defaultWebSocketInterceptor: DefaultWebSocketInterceptor? = null

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        defaultWebSocketHandler?.let {
            registry.addHandler(it,"ws")
                .addInterceptors(defaultWebSocketInterceptor)
                .setAllowedOrigins("*")
        }
    }
}