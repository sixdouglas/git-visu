package org.sixdouglas.git.environment

import org.sixdouglas.git.server.IServerComponent
import org.sixdouglas.git.server.Server
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
internal class EnvironmentComponent internal constructor(internal val environmentRepository: EnvironmentRepository
                                    , internal val serverComponent: IServerComponent) : IEnvironmentComponent {

    override fun addEnvironment(environment: Environment) : Mono<Environment> {
        if (environment.id != null) {
            return Mono.error(EnvironmentIdAutomaticallyGeneratedException())
        } else {
            if (environment.color == null || environment.color.isBlank()) {
                return environmentRepository.save(Environment(null, environment.name, EnvironmentColor.randomColor(), environment.code, environment.role, environment.weight))
            } else {
                return environmentRepository.save(environment)
            }
        }
    }

    override fun addEnvironmentServer(id: Int, server: Server) : Mono<Server> {
        return environmentRepository.existsById(id)
                .flatMap { environmentExists ->
                    if (environmentExists)
                        if (server.id != null)
                            serverComponent.getServer(server.id)
                        else
                            Mono.empty()
                    else
                        Mono.error(Exception("Environement not found"))
                }
                .defaultIfEmpty(Server(null, server.name, server.fullName, server.role, server.environmentId))
                .flatMap { foundServer -> serverComponent.save(Server(foundServer.id, foundServer.name, foundServer.fullName, foundServer.role, id)) }
    }

    override fun getEnvironments() = environmentRepository.findAll()

    override fun getEnvironmentServers(id: Int) = serverComponent.findServersByEnvironmentId(id)

    override fun updateEnvironment(id: Int, environment: Environment) = environmentRepository.save(environment)

    override fun deleteEnvironment(id: Int) = environmentRepository.deleteById(id)
}