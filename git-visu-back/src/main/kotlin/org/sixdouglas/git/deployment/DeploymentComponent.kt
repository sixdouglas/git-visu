package org.sixdouglas.git.deployment

import reactor.util.Logger
import org.sixdouglas.git.artifact.IArtifactComponent
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.IBuildComponent
import org.sixdouglas.git.installation.Installation
import org.sixdouglas.git.installation.InstallationRepository
import org.sixdouglas.git.module.IModuleComponent
import org.sixdouglas.git.server.IServerComponent
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.util.Loggers
import java.time.LocalDateTime

@Service
internal class DeploymentComponent internal constructor(internal val moduleComponent: IModuleComponent
                                   , internal val buildComponent: IBuildComponent
                                   , internal val serverComponent: IServerComponent
                                   , internal val artifactComponent: IArtifactComponent
                                   , internal val installationRepository: InstallationRepository
                                   , internal val deploymentRepository: DeploymentRepository
) : IDeploymentComponent {

    val logger: Logger = Loggers.getLogger("org.sixdouglas.git.deployment.DeploymentController")

    override fun addDeployment(deployment: ArtifactDeployment) : Mono<Deployment> {
        return findModule(deployment, DeploymentInfo(-1, -1, -1, -1, -1))
                .flatMap { deploymentInfo -> findServer(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findArtifact(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findOrCreateInstallation(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findOrCreateBuild(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> deploymentRepository.save(Deployment(null, deploymentInfo.buildId, deploymentInfo.installationId, deployment.deploymentDate)) }
    }

    override fun findModule (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return moduleComponent.findModuleByCode(deployment.moduleCode)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, module not found for code: " + deployment.moduleCode)))
                .map { module -> DeploymentInfo(module.id!!, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    override fun findServer(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return serverComponent.findServerByName(deployment.serverName)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, server not found for name: " + deployment.serverName)))
                .map { server -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, server.id!!, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    override fun findArtifact (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return artifactComponent.findArtifactByName(deployment.artifactName)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, artifact not found for name: " + deployment.artifactName)))
                .map { artifact -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, artifact.id!!, deploymentInfo.installationId) }
    }

    override fun findOrCreateInstallation(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.artifactId == -1 || deploymentInfo.serverId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Artifact and Server required for Installation"))
        }
        return installationRepository.findInstallationByArtifactIdAndAndServerId(deploymentInfo.artifactId, deploymentInfo.serverId)
                .switchIfEmpty(installationRepository.save(Installation(id=null, artifactId = deploymentInfo.artifactId, serverId = deploymentInfo.serverId, port = null, profile = null)))
                .map { installation -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, installation.id!!) }
    }

    override fun findOrCreateBuild (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.moduleId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Module required for Build"))
        }
        return buildComponent.findBuildByNameAndModuleId(deployment.buildName, deploymentInfo.moduleId)
                .switchIfEmpty(buildComponent.save(Build(id = null, name=deployment.buildName, branch=deployment.buildBranch, buildDate = LocalDateTime.now(), moduleId = deploymentInfo.moduleId)))
                .map {build -> DeploymentInfo(deploymentInfo.moduleId, build.id!!, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    override fun getDeployments() = deploymentRepository.findAll()

    override fun updateDeployment(id: Int, deployment: Deployment): Mono<Deployment> {
        val deploymentToBeSaved = Deployment(id, deployment.buildId, deployment.installationId, deployment.deploymentDate)
        return deploymentRepository.save(deploymentToBeSaved)
    }

    override fun deleteDeployment(id: Int) = deploymentRepository.deleteById(id)
}