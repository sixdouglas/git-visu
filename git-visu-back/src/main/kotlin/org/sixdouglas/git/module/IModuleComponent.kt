package org.sixdouglas.git.module

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.build.Build
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IModuleComponent {
    fun addModule(module: Module) : Mono<Module>
    fun getModules(): Flux<Module>
    fun updateModule(id: Int, module: Module): Mono<Module>
    fun deleteModule(id: Int): Mono<Void>
    fun findModuleByCode(moduleCode: String): Mono<Module>
    fun save(module: Module): Mono<Module>
}