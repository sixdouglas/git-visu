package org.sixdouglas.git.deployment

import reactor.core.publisher.Flux

interface IServerDeploymentComponent {
    fun findLatestDeploymentsForServer(serverId: Int): Flux<ServerDeployment>
}