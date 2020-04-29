package org.sixdouglas.git.build

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IBuildComponent {
    fun addBuild(moduleBuild: ModuleBuild) : Mono<Build>
    fun getBuilds(): Flux<Build>
    fun updateBuild(id: Int, build: Build): Mono<Build>
    fun deleteBuild(id: Int): Mono<Void>
    fun findBuildsByModuleId(id: Int): Flux<Build>
    fun findBuildByNameAndModuleId(buildName: String, moduleId: Int): Mono<Build>
    fun save(build: Build): Mono<out Build>
}