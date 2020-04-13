package org.sixdouglas.git.environment

import org.sixdouglas.git.server.Server
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class EnvironmentHandler (val environmentService: EnvironmentService) {
    fun getEnvironments(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.getEnvironments(), Environment::class.java)
    }

    fun getEnvironmentServers(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val environmentId:Int = Integer.valueOf(serverRequest.pathVariable("environmentId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.getEnvironmentServers(environmentId), Server::class.java)
    }

    fun addEnvironment(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestEnvironment: Mono<Environment> = serverRequest.bodyToMono(Environment::class.java)
        return requestEnvironment.flatMap { environment ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.addEnvironment(environment), Environment::class.java) }
    }

    fun addEnvironmentServer(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val environmentId:Int = Integer.valueOf(serverRequest.pathVariable("environmentId"))
        val requestServer: Mono<Server> = serverRequest.bodyToMono(Server::class.java)
        return requestServer.flatMap { server ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.addEnvironmentServer(environmentId, server), Server::class.java) }
    }

    fun updateEnvironment(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val environmentId:Int = Integer.valueOf(serverRequest.pathVariable("environmentId"))
        val requestEnvironment: Mono<Environment> = serverRequest.bodyToMono(Environment::class.java)
        return requestEnvironment.flatMap { environment -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.updateEnvironment(environmentId, environment), Environment::class.java) }
    }

    fun deleteEnvironment(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val environmentId:Int = Integer.valueOf(serverRequest.pathVariable("environmentId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(environmentService.deleteEnvironment(environmentId), Environment::class.java)
    }

}