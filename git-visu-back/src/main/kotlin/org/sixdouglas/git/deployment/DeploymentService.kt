package org.sixdouglas.git.deployment

import reactor.util.Logger
import org.sixdouglas.git.artifact.ArtifactRepository
import org.sixdouglas.git.build.BuildRepository
import org.sixdouglas.git.installation.InstallationRepository
import org.sixdouglas.git.module.ModuleRepository
import org.sixdouglas.git.server.ServerRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.util.Loggers

@Service
class DeploymentService(val moduleRepository: ModuleRepository, val buildRepository: BuildRepository, val serverRepository: ServerRepository
                        , val artifactRepository: ArtifactRepository, val installationRepository: InstallationRepository
                        , val deploymentRepository: DeploymentRepository) {

    val logger: Logger = Loggers.getLogger("org.sixdouglas.git.deployment.DeploymentController")

    fun addDeployment(deployment: ArtifactDeployment) : Mono<Deployment> {
        val deploymentInfo = DeploymentInfo(-1, -1, -1, -1, -1)
        return moduleRepository.findModuleByCode(deployment.moduleCode)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Deployment not found for module: " + deployment.moduleCode)))
                .flatMap { module ->
                    deploymentInfo.moduleId = module.id!!
                    logger.info(deploymentInfo.toString())
                    buildRepository.findBuildByNameAndModuleId(deployment.buildName, deploymentInfo.moduleId)
                }
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Deployment not found for build: " + deployment.buildName)))
                .flatMap {build ->
                    deploymentInfo.buildId = build.id!!
                    logger.info(deploymentInfo.toString())
                    serverRepository.findServerByName(deployment.serverName)
                }
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Deployment not found for server: " + deployment.serverName)))
                .flatMap { server ->
                    deploymentInfo.serverId = server.id!!
                    logger.info(deploymentInfo.toString())
                    artifactRepository.findArtifactByName(deployment.artifactName)
                }
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Deployment not found for artifact: " + deployment.artifactName)))
                .flatMap { artifact ->
                    deploymentInfo.artifactId = artifact.id!!
                    logger.info(deploymentInfo.toString())
                    installationRepository.findInstallationByArtifactIdAndAndServerId(deploymentInfo.artifactId, deploymentInfo.serverId)
                }
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Deployment not found for artifact: " + deploymentInfo.artifactId + " and server: " + deploymentInfo.serverId)))
                .flatMap { installation ->
                    deploymentInfo.installationId = installation.id!!
                    logger.info(deploymentInfo.toString())
                    deploymentRepository.save(Deployment(null, deploymentInfo.buildId, deploymentInfo.installationId, deployment.deploymentDate))
                }
    }

    fun getDeployments() = deploymentRepository.findAll()

    fun updateDeployment(id: Int, deployment: Deployment): Mono<Deployment> {
        val deploymentToBeSaved = Deployment(id, deployment.buildId, deployment.installationId, deployment.deploymentDate)
        return deploymentRepository.save(deploymentToBeSaved)
    }

    fun deleteDeployment(id: Int) = deploymentRepository.deleteById(id)

    data class DeploymentInfo(var moduleId: Int, var buildId: Int, var serverId: Int, var artifactId: Int, var installationId: Int)
}