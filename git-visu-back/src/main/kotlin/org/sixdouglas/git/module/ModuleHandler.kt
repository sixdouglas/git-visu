package org.sixdouglas.git.module

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.module.Module
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ModuleHandler (val moduleService: ModuleService) {
    fun getModules(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.getModules(), Module::class.java)
    }

    fun getModuleArtifacts(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.getModuleArtifacts(moduleId), Artifact::class.java)
    }

    fun getModuleBuilds(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.getModuleBuilds(moduleId), Build::class.java)
    }

    fun addModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestModule: Mono<Module> = serverRequest.bodyToMono(Module::class.java)
        return requestModule.flatMap { module ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.addModule(module), Module::class.java) }
    }

    fun updateModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        val requestModule: Mono<Module> = serverRequest.bodyToMono(Module::class.java)
        return requestModule.flatMap { module -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.updateModule(moduleId, module), Module::class.java) }
    }

    fun deleteModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleService.deleteModule(moduleId), Module::class.java)
    }

}