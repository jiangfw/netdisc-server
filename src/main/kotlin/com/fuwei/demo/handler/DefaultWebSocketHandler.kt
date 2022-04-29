package com.fuwei.demo.handler

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession

@Component
class DefaultWebSocketHandler : WebSocketHandler {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        // 建立连接
        println("afterConnectionEstablished 建立连接")
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        // 接受消息
        println("handleMessage 接受消息")

    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        // 传输错误，清空用户缓存信息
        println("handleTransportError 传输错误，清空用户缓存信息")

    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        // 关闭连接，清空用户缓存信息
        println("afterConnectionClosed 关闭连接，清空用户缓存信息")

    }

    override fun supportsPartialMessages(): Boolean {
        return true
    }
}