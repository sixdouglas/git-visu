package org.sixdouglas.git.server

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ServerRepository : ReactiveCrudRepository<Server, Int> {
    fun findServersByEnvironmentId(environmentId: Int): Flux<Server>
    fun findServerByName(name: String): Mono<Server>
}