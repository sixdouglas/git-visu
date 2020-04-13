package org.sixdouglas.git.deployment

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface DeploymentRepository : ReactiveCrudRepository<Deployment, Int> {
    fun findFirstByBuildIdAndInstallationIdOrderByDeploymentDateDesc(artifactId: Int, serverId: Int): Mono<Deployment>
}