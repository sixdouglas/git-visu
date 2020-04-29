package org.sixdouglas.git.server

import org.sixdouglas.git.deployment.ServerDeployment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IServerComponent {
    fun addServer(server: Server) : Mono<Server>
    fun updateServer(id: Int, server: Server): Mono<Server>
    fun getServerDeployments(id: Int): Flux<ServerDeployment>
    fun getServers(): Flux<Server>
    fun deleteServer(id: Int): Mono<Void>
    fun getServer(id: Int): Mono<Server>
    fun save(server: Server): Mono<Server>
    fun findServersByEnvironmentId(id: Int): Flux<Server>
    fun findServerByName(serverName: String): Mono<Server>
}