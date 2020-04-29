package org.sixdouglas.git.routing

import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.BuildComponent
import org.sixdouglas.git.build.ModuleBuild
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
internal class BuildHandler internal constructor(internal val buildService: BuildComponent) {
    fun getBuilds(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(buildService.getBuilds(), Build::class.java)
    }
    fun getModuleBuilds(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(buildService.findBuildsByModuleId(moduleId), Build::class.java)
    }

    fun addBuild(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestBuild: Mono<ModuleBuild> = serverRequest.bodyToMono(ModuleBuild::class.java)
        return requestBuild.flatMap { build ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(buildService.addBuild(build), Build::class.java) }
    }

    fun updateBuild(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val buildId:Int = Integer.valueOf(serverRequest.pathVariable("buildId"))
        val requestBuild: Mono<Build> = serverRequest.bodyToMono(Build::class.java)
        return requestBuild.flatMap { build -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(buildService.updateBuild(buildId, build), Build::class.java) }
    }

    fun deleteBuild(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val buildId:Int = Integer.valueOf(serverRequest.pathVariable("buildId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(buildService.deleteBuild(buildId), Build::class.java)
    }
}