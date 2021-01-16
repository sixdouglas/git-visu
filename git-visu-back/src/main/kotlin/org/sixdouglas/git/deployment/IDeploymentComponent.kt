package org.sixdouglas.git.deployment

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IDeploymentComponent {
    fun addDeployment(deployment: ArtifactDeployment) : Mono<Deployment>
    fun findModule (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo>
    fun findServer(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo>
    fun findArtifact (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo>
    fun findOrCreateInstallation(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo>
    fun findOrCreateBuild (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo>
    fun getDeployments(): Flux<Deployment>
    fun updateDeployment(id: Int, deployment: Deployment): Mono<Deployment>
    fun deleteDeployment(id: Int): Mono<Void>
    fun getActualDeployments(): Flux<ActualDeployment>
}