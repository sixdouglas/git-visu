package org.sixdouglas.git.routing

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.artifact.ArtifactComponent
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.IBuildComponent
import org.sixdouglas.git.module.Module
import org.sixdouglas.git.module.IModuleComponent
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
internal class ModuleHandler internal constructor (internal val moduleComponent: IModuleComponent,internal val artifactComponent: ArtifactComponent) {
    fun getModules(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleComponent.getModules(), Module::class.java)
    }

    fun addModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestModule: Mono<Module> = serverRequest.bodyToMono(Module::class.java)
        return requestModule.flatMap { module ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleComponent.addModule(module), Module::class.java) }
    }

    fun updateModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        val requestModule: Mono<Module> = serverRequest.bodyToMono(Module::class.java)
        return requestModule.flatMap { module -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleComponent.updateModule(moduleId, module), Module::class.java) }
    }

    fun deleteModule(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(moduleComponent.deleteModule(moduleId), Module::class.java)
    }

}