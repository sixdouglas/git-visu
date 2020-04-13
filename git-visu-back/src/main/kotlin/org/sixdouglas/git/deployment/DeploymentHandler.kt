package org.sixdouglas.git.deployment

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DeploymentHandler (val deploymentService: DeploymentService) {
    fun getDeployments(deploymentRequest: ServerRequest): Mono<out ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deploymentService.getDeployments(), Deployment::class.java)
    }

    fun addDeployment(deploymentRequest: ServerRequest): Mono<out ServerResponse> {
        val requestDeployment: Mono<ArtifactDeployment> = deploymentRequest.bodyToMono(ArtifactDeployment::class.java)
        return requestDeployment.flatMap { deployment ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deploymentService.addDeployment(deployment), Deployment::class.java) }
    }

    fun updateDeployment(deploymentRequest: ServerRequest): Mono<out ServerResponse> {
        val deploymentId:Int = Integer.valueOf(deploymentRequest.pathVariable("deploymentId"))
        val requestDeployment: Mono<Deployment> = deploymentRequest.bodyToMono(Deployment::class.java)
        return requestDeployment.flatMap { deployment -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deploymentService.updateDeployment(deploymentId, deployment), Deployment::class.java) }
    }

    fun deleteDeployment(deploymentRequest: ServerRequest): Mono<out ServerResponse> {
        val deploymentId:Int = Integer.valueOf(deploymentRequest.pathVariable("deploymentId"))
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(deploymentService.deleteDeployment(deploymentId), Deployment::class.java)
    }

}