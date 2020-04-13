package org.sixdouglas.git.environment

import org.sixdouglas.git.server.*
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Service
class EnvironmentService(val environmentRepository: EnvironmentRepository, val serverRepository: ServerRepository) {

    fun addEnvironment(environment: Environment) : Mono<Environment> {
        if (environment.id != null) {
            return Mono.error(EnvironmentIdAutomaticallyGeneratedException())
        } else {
            if (environment.color == null || environment.color.isBlank()) {
                return environmentRepository.save(Environment(null, environment.name, EnvironmentColor.randomColor(), environment.code, environment.weight))
            } else {
                return environmentRepository.save(environment)
            }
        }
    }

    fun addEnvironmentServer(id: Int, server: Server) : Mono<Server> {
        return environmentRepository.existsById(id)
                .flatMap { environmentExists ->
                    if (environmentExists)
                        if (server.id != null)
                            serverRepository.findById(server.id)
                        else
                            Mono.empty()
                    else
                        Mono.error(Exception("Environement not found"))
                }
                .defaultIfEmpty(Server(null, server.name, server.fullName, server.role, server.environmentId))
                .flatMap { foundServer -> serverRepository.save(Server(foundServer.id, foundServer.name, foundServer.fullName, foundServer.role, id)) }
    }

    fun getEnvironments() = environmentRepository.findAll()

    fun getEnvironmentServers(id: Int) = serverRepository.findServersByEnvironmentId(id)

    fun updateEnvironment(id: Int, environment: Environment) = environmentRepository.save(environment)

    fun deleteEnvironment(id: Int) = environmentRepository.deleteById(id)
}