package org.sixdouglas.git.server

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.deployment.Deployment
import org.sixdouglas.git.deployment.ServerDeployment
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ServerHandler (val serverService: ServerService) {
    fun getServers(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverService.getServers(), Server::class.java)
    }

    fun getServerDeployments(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val serverId:Int = Integer.valueOf(serverRequest.pathVariable("serverId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverService.getServerDeployments(serverId), ServerDeployment::class.java)
    }

    fun addServer(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestServer: Mono<Server> = serverRequest.bodyToMono(Server::class.java)
        return requestServer.flatMap { server ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverService.addServer(server), Server::class.java) }
    }

    fun updateServer(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val serverId:Int = Integer.valueOf(serverRequest.pathVariable("serverId"))
        val requestServer: Mono<Server> = serverRequest.bodyToMono(Server::class.java)
        return requestServer.flatMap { server -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverService.updateServer(serverId, server), Server::class.java) }
    }

    fun deleteServer(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val serverId:Int = Integer.valueOf(serverRequest.pathVariable("serverId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(serverService.deleteServer(serverId), Server::class.java)
    }

}