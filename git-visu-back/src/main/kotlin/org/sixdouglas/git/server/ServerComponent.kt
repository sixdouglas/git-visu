package org.sixdouglas.git.server

import org.sixdouglas.git.deployment.IServerDeploymentComponent
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
internal class ServerComponent internal constructor (internal val serverRepository: ServerRepository
                                , internal val serverDeploymentService: IServerDeploymentComponent) : IServerComponent {

    override fun addServer(server: Server) : Mono<Server> {
        if (server.id != null) {
            return Mono.error(ServerIdAutomaticallyGeneratedException())
        } else {
            return serverRepository.save(server)
        }
    }

    override fun updateServer(id: Int, server: Server): Mono<Server> {
        val serverToBeSaved = Server(id, server.name, server.fullName, server.role, server.environmentId)
        return serverRepository.save(serverToBeSaved)
    }

    override fun getServerDeployments(id: Int) = serverDeploymentService.findLatestDeploymentsForServer(id)

    override fun getServers() = serverRepository.findAll()
    override fun deleteServer(id: Int) = serverRepository.deleteById(id)
    override fun getServer(id: Int) = serverRepository.findById(id)
    override fun save(server: Server) = serverRepository.save(server)
    override fun findServersByEnvironmentId(id: Int) = serverRepository.findServersByEnvironmentId(id)
    override fun findServerByName(serverName: String) = serverRepository.findServerByName(serverName)
}