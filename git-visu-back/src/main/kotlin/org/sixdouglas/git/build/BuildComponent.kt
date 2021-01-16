package org.sixdouglas.git.build

import org.sixdouglas.git.module.IModuleComponent
import org.sixdouglas.git.module.Module
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.Logger
import reactor.util.Loggers
import java.time.OffsetDateTime

@Service
internal class BuildComponent internal constructor(internal val buildRepository: BuildRepository
                                                   , internal val moduleComponent: IModuleComponent
                                                   , internal val client: DatabaseClient) : IBuildComponent {

    val logger: Logger = Loggers.getLogger("com.adeo.gestachats.gitvisu.build")

    val deploymentServersQuery = "select e.name env_name, e.weight, e.color, s.name server_name, a.name artifact_name, i.port, i.profile, max(d.deployment_date) deployment_date " +
            " from deployment d" +
                " left outer join installation i on d.installation_id = i.id " +
                " left outer join artifact a on a.id = i.artifact_id " +
                " left outer join server s on s.id = i.server_id " +
                " left outer join environment e on e.id = s.environment_id " +
            " where d.build_id = :buildId " +
            " group by e.name, e.weight, e.color, s.name, a.name, i.port, i.profile " +
            " order by e.weight, s.name, a.name, deployment_date"

    override fun addBuild(moduleBuild: ModuleBuild) : Mono<Build> {
        return moduleComponent.findModuleByCode(moduleBuild.moduleCode)
                .log(logger)
                .switchIfEmpty( moduleComponent.save(Module(null, moduleBuild.moduleCode, moduleBuild.moduleCode)))
                .log(logger)
                .flatMap { module -> buildRepository.findBuildByNameAndModuleId(moduleBuild.name, module.id!!)}
                .log(logger)
                .switchIfEmpty(
                        moduleComponent.findModuleByCode(moduleBuild.moduleCode)
                                .log(logger)
                                .flatMap{ module -> save(Build(null, moduleBuild.name, moduleBuild.branch, moduleBuild.buildDate, module.id))
                                .log(logger)
                                }
                )
    }

    override fun getBuilds() = buildRepository.findAll()

    override fun updateBuild(id: Int, build: Build): Mono<Build> {
        val buildToBeSaved = Build(id, build.name, build.branch, build.buildDate, build.moduleId)
        return save(buildToBeSaved)
    }

    override fun deleteBuild(id: Int) = buildRepository.deleteById(id)
    override fun findBuildsByModuleId(id: Int) = buildRepository.findBuildsByModuleId(id)
    override fun findBuildByNameAndModuleId(buildName: String, moduleId: Int) = buildRepository.findBuildByNameAndModuleId(cleanBuildName(buildName), moduleId)
    override fun save(build: Build) = buildRepository.save(cleanBuild(build))

    fun cleanBuild(build: Build): Build {
        return Build(build.id, cleanBuildName(build.name), build.branch, build.buildDate, build.moduleId, build.name.contains("SNAPSHOT"))
    }

    fun cleanBuildName(buildName: String): String {
        return if (buildName.endsWith("-1")){
            buildName.dropLast(2)
        } else {
            buildName
        }
    }

    override fun getBuildDeployments(buildId: Int): Flux<Environment> {
        return client
            .sql(deploymentServersQuery)
            .bind("buildId", buildId)
            .map{row -> BuildDeployment(
                row.get("env_name") as String,
                row.get("weight") as Int,
                row.get("color") as String,
                row.get("server_name") as String,
                row.get("artifact_name") as String,
                row.get("port") as? String,
                row.get("profile") as? String,
                row.get("deployment_date") as? OffsetDateTime
            ) }
            .all()
            .groupBy { Environment(it.envName, it.weight, it.color, null) }
            .flatMap{ environment ->
                environment.groupBy { Server(it.serverName, null) }
                    .flatMap { serverGroup -> serverGroup.flatMap { server -> Mono.just(Artifact(server.artifactName, Deployment(server.port, server.profile, server.deploymentDate))) }
                        .collectList()
                        .map { artifactList -> Server(serverGroup.key().name, artifactList) }
                    }
                    .collectList()
                    .map { list -> Environment(environment.key().name, environment.key().weight, environment.key().color, list) }
            }

    }
}