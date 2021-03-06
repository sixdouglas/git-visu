package org.sixdouglas.git.routing

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.artifact.ArtifactComponent
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
internal class ArtifactHandler internal constructor(internal val artifactService: ArtifactComponent) {
    fun getArtifacts(serverRequest: ServerRequest): Mono<out ServerResponse> {
        return ok().contentType(APPLICATION_JSON).body(artifactService.getArtifacts(), Artifact::class.java)
    }

    fun addArtifact(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val requestArtifact: Mono<Artifact> = serverRequest.bodyToMono(Artifact::class.java)
        return requestArtifact.flatMap { artifact ->  ok().contentType(APPLICATION_JSON).body(artifactService.addArtifact(artifact), Artifact::class.java) }
    }

    fun getModuleArtifacts(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val moduleId:Int = Integer.valueOf(serverRequest.pathVariable("moduleId"))
        return ok().contentType(APPLICATION_JSON).body(artifactService.findArtifactsByModuleId(moduleId), Artifact::class.java)
    }

    fun updateArtifact(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val artifactId:Int = Integer.valueOf(serverRequest.pathVariable("artifactId"))
        val requestArtifact: Mono<Artifact> = serverRequest.bodyToMono(Artifact::class.java)
        return requestArtifact.flatMap { artifact -> ok().contentType(APPLICATION_JSON).body(artifactService.updateArtifact(artifactId, artifact), Artifact::class.java) }
    }

    fun deleteArtifact(serverRequest: ServerRequest): Mono<out ServerResponse> {
        val artifactId:Int = Integer.valueOf(serverRequest.pathVariable("artifactId"))
        return ok().contentType(APPLICATION_JSON).body(artifactService.deleteArtifact(artifactId), Artifact::class.java)
    }
}