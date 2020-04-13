package org.sixdouglas.git.artifact

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ArtifactRepository : ReactiveCrudRepository<Artifact, Int> {
    fun findArtifactsByModuleId(moduleId: Int) : Flux<Artifact>
    fun findArtifactByName(name: String): Mono<Artifact>
}