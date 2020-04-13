package org.sixdouglas.git.server

import org.sixdouglas.git.deployment.ServerDeploymentService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ServerService(val serverRepository: ServerRepository, val serverDeploymentService: ServerDeploymentService) {

    fun addServer(server: Server) : Mono<Server> {
        if (server.id != null) {
            return Mono.error(ServerIdAutomaticallyGeneratedException())
        } else {
            return serverRepository.save(server)
        }
    }

    fun getServers() = serverRepository.findAll()

    fun getServerDeployments(id: Int) = serverDeploymentService.findLatestDeploymentsForServer(id)

    fun updateServer(id: Int, server: Server): Mono<Server> {
        val serverToBeSaved = Server(id, server.name, server.fullName, server.role, server.environmentId)
        return serverRepository.save(serverToBeSaved)
    }

    fun deleteServer(id: Int) = serverRepository.deleteById(id)
}