package org.sixdouglas.git.installation

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface InstallationRepository : ReactiveCrudRepository<Installation, Int> {
    fun findInstallationByArtifactIdAndAndServerId(artifactId: Int, serverId: Int): Mono<Installation>
}