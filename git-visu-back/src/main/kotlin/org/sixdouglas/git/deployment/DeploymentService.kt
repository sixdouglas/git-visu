package org.sixdouglas.git.deployment

import reactor.util.Logger
import org.sixdouglas.git.artifact.ArtifactRepository
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.BuildRepository
import org.sixdouglas.git.installation.Installation
import org.sixdouglas.git.installation.InstallationRepository
import org.sixdouglas.git.module.ModuleRepository
import org.sixdouglas.git.server.ServerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.util.Loggers
import java.time.LocalDateTime

@Service
class DeploymentService(val moduleRepository: ModuleRepository, val buildRepository: BuildRepository, val serverRepository: ServerRepository
                        , val artifactRepository: ArtifactRepository, val installationRepository: InstallationRepository
                        , val deploymentRepository: DeploymentRepository) {

    val logger: Logger = Loggers.getLogger("org.sixdouglas.git.deployment.DeploymentController")

    fun addDeployment(deployment: ArtifactDeployment) : Mono<Deployment> {
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

    fun findModule (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return moduleRepository.findModuleByCode(deployment.moduleCode)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, module not found for code: " + deployment.moduleCode)))
                .map { module -> DeploymentInfo(module.id!!, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    fun findServer(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return serverRepository.findServerByName(deployment.serverName)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, server not found for name: " + deployment.serverName)))
                .map { server -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, server.id!!, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    fun findArtifact (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return artifactRepository.findArtifactByName(deployment.artifactName)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, artifact not found for name: " + deployment.artifactName)))
                .map { artifact -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, artifact.id!!, deploymentInfo.installationId) }
    }

    fun findOrCreateInstallation(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.artifactId == -1 || deploymentInfo.serverId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Artifact and Server required for Installation"))
        }
        return installationRepository.findInstallationByArtifactIdAndAndServerId(deploymentInfo.artifactId, deploymentInfo.serverId)
                .switchIfEmpty(installationRepository.save(Installation(id=null, artifactId = deploymentInfo.artifactId, serverId = deploymentInfo.serverId, port = null, profile = null)))
                .map { installation -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, installation.id!!) }
    }

    fun findOrCreateBuild (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.moduleId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Module required for Build"))
        }
        return buildRepository.findBuildByNameAndModuleId(deployment.buildName, deploymentInfo.moduleId)
                .switchIfEmpty(buildRepository.save(Build(id = null, name=deployment.buildName, branch="", buildDate = LocalDateTime.now(), moduleId = deploymentInfo.moduleId)))
                .map {build -> DeploymentInfo(deploymentInfo.moduleId, build.id!!, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.installationId) }
    }

    fun getDeployments() = deploymentRepository.findAll()

    fun updateDeployment(id: Int, deployment: Deployment): Mono<Deployment> {
        val deploymentToBeSaved = Deployment(id, deployment.buildId, deployment.installationId, deployment.deploymentDate)
        return deploymentRepository.save(deploymentToBeSaved)
    }

    fun deleteDeployment(id: Int) = deploymentRepository.deleteById(id)

    data class DeploymentInfo(var moduleId: Int, var buildId: Int, var serverId: Int, var artifactId: Int, var installationId: Int)
}