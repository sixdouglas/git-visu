package org.sixdouglas.git.environment

import org.sixdouglas.git.server.Server
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IEnvironmentComponent {
    fun addEnvironment(environment: Environment) : Mono<Environment>
    fun addEnvironmentServer(id: Int, server: Server) : Mono<Server>
    fun getEnvironments(): Flux<Environment>
    fun getEnvironmentServers(id: Int): Flux<Server>
    fun updateEnvironment(id: Int, environment: Environment): Mono<Environment>
    fun deleteEnvironment(id: Int): Mono<Void>
}