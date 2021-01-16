package org.sixdouglas.git.server

import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal interface ServerRepository : ReactiveSortingRepository<Server, Int> {
    fun findServersByEnvironmentId(environmentId: Int, sort: Sort): Flux<Server>
    @Query("select * from server where name = :name and :role = Any(role)")
    fun findServerByNameAndRole(name: String, role: String): Mono<Server>
}