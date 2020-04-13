package org.sixdouglas.git.config

import org.sixdouglas.git.artifact.ArtifactHandler
import org.sixdouglas.git.build.BuildHandler
import org.sixdouglas.git.environment.EnvironmentHandler
import org.sixdouglas.git.module.ModuleHandler
import org.sixdouglas.git.server.ServerHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfiguration {
    @Bean
    fun routerFunction(
            artifactHandler: ArtifactHandler,
            buildHandler: BuildHandler,
            environmentHandler: EnvironmentHandler,
            moduleHandler: ModuleHandler,
            serverHandler: ServerHandler,
            @Value("classpath:/static/index.html") html: Resource
    ): RouterFunction<ServerResponse> = router {
        accept(TEXT_HTML).nest {
            GET("/") { ok().contentType(TEXT_HTML).bodyValue(html) }
        }
        accept(APPLICATION_JSON).nest {
            "/artifacts".nest {
                GET("/", artifactHandler::getArtifacts)
                POST("/", artifactHandler::addArtifact)
                PUT("/{artifactId}", artifactHandler::updateArtifact)
                DELETE("/{artifactId}", artifactHandler::deleteArtifact)
            }
            "/builds".nest {
                GET("/", buildHandler::getBuilds)
                POST("/", buildHandler::addBuild)
                PUT("/{buildId}", buildHandler::updateBuild)
                DELETE("/{buildId}", buildHandler::deleteBuild)
            }
            "/environments".nest {
                GET("/{environmentId}/servers", environmentHandler::getEnvironmentServers)
                GET("/", environmentHandler::getEnvironments)
                POST("/", environmentHandler::addEnvironment)
                POST("/{environmentId}/servers", environmentHandler::addEnvironmentServer)
                PUT("/{environmentId}", environmentHandler::updateEnvironment)
                DELETE("/{environmentId}", environmentHandler::deleteEnvironment)
            }
            "/modules".nest {
                GET("/{moduleId}/artifacts", moduleHandler::getModuleArtifacts)
                GET("/{moduleId}/builds", moduleHandler::getModuleBuilds)
                GET("/", moduleHandler::getModules)
                POST("/", moduleHandler::addModule)
                PUT("/{moduleId}", moduleHandler::updateModule)
                DELETE("/{moduleId}", moduleHandler::deleteModule)
            }
            "/servers".nest {
                GET("/{serverId}/deployments", serverHandler::getServerDeployments)
                GET("/", serverHandler::getServers)
                POST("/", serverHandler::addServer)
                PUT("/{serverId}", serverHandler::updateServer)
                DELETE("/{serverId}", serverHandler::deleteServer)
            }
            "/deployments".nest {
                GET("/", serverHandler::getServers)
                POST("/", serverHandler::addServer)
                PUT("/{deploymentId}", serverHandler::updateServer)
                DELETE("/{deploymentId}", serverHandler::deleteServer)
            }
        }
        resources("/**", ClassPathResource("static/"))
    }
}