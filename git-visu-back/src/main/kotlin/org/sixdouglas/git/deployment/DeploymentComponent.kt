package org.sixdouglas.git.deployment

import reactor.util.Logger
import org.sixdouglas.git.artifact.IArtifactComponent
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.BuildDeployment
import org.sixdouglas.git.build.Environment
import org.sixdouglas.git.build.IBuildComponent
import org.sixdouglas.git.installation.Installation
import org.sixdouglas.git.installation.InstallationRepository
import org.sixdouglas.git.module.IModuleComponent
import org.sixdouglas.git.server.IServerComponent
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.Loggers
import java.time.OffsetDateTime

@Service
internal class DeploymentComponent internal constructor(internal val moduleComponent: IModuleComponent
                                   , internal val buildComponent: IBuildComponent
                                   , internal val serverComponent: IServerComponent
                                   , internal val artifactComponent: IArtifactComponent
                                   , internal val installationRepository: InstallationRepository
                                   , internal val deploymentRepository: DeploymentRepository
                                   , internal val client: DatabaseClient
) : IDeploymentComponent {

    val logger: Logger = Loggers.getLogger("com.adeo.gestachats.gitvisu.deployment")

    val deploymentServersQuery = "" +
    " select mod.name as module_name, mod.code as module_code, art.name as component, env.name as env_name, env.weight as env_weight, bld.branch as branch, bld.name as version" +
    " from deployment dep" +
    " inner join build bld on bld.id = dep.build_id" +
    " inner join module mod on mod.id = bld.module_id" +
    " inner join artifact art on mod.id = art.module_id" +
    " inner join installation ins on art.id = ins.artifact_id and dep.installation_id = ins.id" +
    " inner join server srv on srv.id = ins.server_id" +
    " inner join environment env on env.id = srv.environment_id" +
    " where dep.deployment_date = (select max(deployment_date) from deployment dep1 where dep1.installation_id = ins.id)" +
    " group by bld.name, bld.branch, art.name, mod.name, mod.code, env.name, env.code, env.weight" +
    " order by Component, env.weight"

    override fun addDeployment(deployment: ArtifactDeployment) : Mono<Deployment> {
        return findModule(deployment, DeploymentInfo(-1, -1, -1, -1, null, -1))
                .log(logger)
                .flatMap { deploymentInfo -> findArtifact(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findServer(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findOrCreateInstallation(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> findOrCreateBuild(deployment, deploymentInfo) }
                .log(logger)
                .flatMap { deploymentInfo -> deploymentRepository.save(Deployment(null, deploymentInfo.buildId, deploymentInfo.installationId, deployment.deploymentDate)) }
    }

    override fun findModule (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return moduleComponent.findModuleByCode(deployment.moduleCode)
                .log(logger)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, module not found for code: " + deployment.moduleCode)))
                .log(logger)
                .map { module -> DeploymentInfo(module.id!!, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.role, deploymentInfo.installationId) }
    }

    override fun findServer(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return serverComponent.findServerByNameAndRole(deployment.serverName, deploymentInfo.role!!)
                .log(logger)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, server not found for name: " + deployment.serverName + " and role: " + deploymentInfo.role)))
                .log(logger)
                .map { server -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, server.id!!, deploymentInfo.artifactId, deploymentInfo.role, deploymentInfo.installationId) }
    }

    override fun findArtifact (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        return artifactComponent.findArtifactByName(deployment.artifactName)
                .log(logger)
                .switchIfEmpty(Mono.error(DeploymentNotFoundException("Error creating Deployment, artifact not found for name: " + deployment.artifactName)))
                .log(logger)
                .map { artifact -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, artifact.id!!, artifact.role, deploymentInfo.installationId) }
    }

    override fun findOrCreateInstallation(deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.artifactId == -1 || deploymentInfo.serverId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Artifact and Server required for Installation"))
        }
        return installationRepository.findInstallationByArtifactIdAndAndServerId(deploymentInfo.artifactId, deploymentInfo.serverId)
                .log(logger)
                .switchIfEmpty(installationRepository.save(Installation(id=null, artifactId = deploymentInfo.artifactId, serverId = deploymentInfo.serverId, port = null, profile = null)))
                .log(logger)
                .map { installation -> DeploymentInfo(deploymentInfo.moduleId, deploymentInfo.buildId, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.role, installation.id!!) }
    }

    override fun findOrCreateBuild (deployment: ArtifactDeployment, deploymentInfo: DeploymentInfo): Mono<DeploymentInfo> {
        if (deploymentInfo.moduleId == -1) {
            return Mono.error(DeploymentNotFoundException("Error creating Deployment, Module required for Build"))
        }
        return buildComponent.findBuildByNameAndModuleId(deployment.buildName, deploymentInfo.moduleId)
                .log(logger)
                .switchIfEmpty(buildComponent.save(Build(id = null, name=deployment.buildName, branch=deployment.buildBranch, buildDate = OffsetDateTime.now(), moduleId = deploymentInfo.moduleId)))
                .log(logger)
                .map {build -> DeploymentInfo(deploymentInfo.moduleId, build.id!!, deploymentInfo.serverId, deploymentInfo.artifactId, deploymentInfo.role, deploymentInfo.installationId) }
    }

    override fun getDeployments() = deploymentRepository.findAll()

    override fun updateDeployment(id: Int, deployment: Deployment): Mono<Deployment> {
        val deploymentToBeSaved = Deployment(id, deployment.buildId, deployment.installationId, deployment.deploymentDate)
        return deploymentRepository.save(deploymentToBeSaved)
            .log(logger)
    }

    override fun deleteDeployment(id: Int) = deploymentRepository.deleteById(id)


    override fun getActualDeployments(): Flux<ActualDeployment> =
        client
            .sql(deploymentServersQuery)
            .map{row -> ActualDeployment(
                row.get("module_name") as String,
                row.get("module_code") as String,
                row.get("component") as String,
                row.get("env_name") as String,
                row.get("env_weight") as Int,
                row.get("branch") as String,
                row.get("version") as String
            ) }
            .all()
}