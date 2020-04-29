package org.sixdouglas.git.artifact

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IArtifactComponent {
    fun addArtifact(artifact: Artifact) : Mono<Artifact>
    fun getArtifacts(): Flux<Artifact>
    fun updateArtifact(id: Int, artifact: Artifact): Mono<Artifact>
    fun deleteArtifact(id: Int): Mono<Void>
    fun findArtifactsByModuleId(id: Int): Flux<Artifact>
    fun findArtifactByName(artifactName: String): Mono<Artifact>
}