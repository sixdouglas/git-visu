package org.sixdouglas.git.module

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

internal interface ModuleRepository : ReactiveCrudRepository<Module, Int> {
    fun findModuleByName(name: String): Mono<Module>
    fun findModuleByCode(code: String): Mono<Module>
}