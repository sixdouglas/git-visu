package org.sixdouglas.git.build

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BuildRepository : ReactiveCrudRepository<Build, Int> {
    fun findBuildsByModuleId(moduleId: Int): Flux<Build>
    fun findBuildByNameAndModuleId(name: String, moduleId: Int): Mono<Build>
}