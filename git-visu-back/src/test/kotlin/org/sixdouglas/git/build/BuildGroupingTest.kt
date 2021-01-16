package org.sixdouglas.git.build

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.OffsetDateTime

class BuildGroupingTest {
    private val log = LoggerFactory.getLogger(BuildGroupingTest::class.java)
    private val buildDeployment1 = BuildDeployment("test", 10, "#111111", "server1", "artifact1", null, null, OffsetDateTime.now())
    private val buildDeployment2 = BuildDeployment("test", 10, "#111111", "server2", "artifact2", null, null, OffsetDateTime.now())
    private val buildDeployment3 = BuildDeployment("uat", 20, "#666666", "server3", "artifact1", null, null, OffsetDateTime.now())
    private val buildDeployment4 = BuildDeployment("uat", 20, "#666666", "server5", "artifact2", null, null, OffsetDateTime.now())
    private val buildDeployment5 = BuildDeployment("uat", 20, "#666666", "server5", "artifact3", null, null, OffsetDateTime.now())
    private val buildDeploymentList = listOf(buildDeployment1, buildDeployment2, buildDeployment3, buildDeployment4, buildDeployment5)

    @Test
    fun runFlux (){

        val groupBy = Flux.fromIterable(buildDeploymentList)
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

        StepVerifier.create(groupBy)
            .consumeNextWith{ s -> log.info(s.toString()) }
            .consumeNextWith{ s -> log.info(s.toString()) }
            .expectComplete()
            .verify()
    }

    @Test
    fun runFluxGrouped (){

        val groupBy = Flux.fromIterable(buildDeploymentList)
            .groupBy { Environment(it.envName, it.weight, it.color, null) }
            .flatMap{ environment ->
                environment.groupBy { Server(it.serverName, null) }
                    .flatMap { serverGroup -> serverGroup.flatMap { server -> Mono.just(Artifact(server.artifactName, null)) }
                        .collectList()
                        .map { artifactList -> Server(serverGroup.key().name, artifactList) }
                    }
                    .collectList()
                    .map { list -> Environment(environment.key().name, environment.key().weight, environment.key().color, list) }
            }

        StepVerifier.create(groupBy)
            .consumeNextWith{ s -> log.info(s.toString()) }
            .consumeNextWith{ s -> log.info(s.toString()) }
            .expectComplete()
            .verify()
    }
}
