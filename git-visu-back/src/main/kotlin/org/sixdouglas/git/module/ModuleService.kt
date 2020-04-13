package org.sixdouglas.git.module

import org.sixdouglas.git.artifact.Artifact
import org.sixdouglas.git.artifact.ArtifactRepository
import org.sixdouglas.git.build.Build
import org.sixdouglas.git.build.BuildRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ModuleService(val moduleRepository: ModuleRepository
                    , val artifactRepository: ArtifactRepository
                    , val buildRepository: BuildRepository) {

    fun addModule(module: Module) : Mono<Module> {
        if (module.id != null) {
            return Mono.error(ModuleIdAutomaticallyGeneratedException())
        } else {
            return moduleRepository.save(module)
        }
    }

    fun getModules() = moduleRepository.findAll()

    fun updateModule(id: Int, module: Module): Mono<Module> {
        val moduleToBeSaved = Module(id, module.name, module.code)
        return moduleRepository.save(moduleToBeSaved)
    }

    fun getModuleArtifacts(id: Int): Flux<Artifact> {
        return artifactRepository.findArtifactsByModuleId(id)
    }

    fun getModuleBuilds(id: Int): Flux<Build> {
        return buildRepository.findBuildsByModuleId(id)
    }

    fun deleteModule(id: Int) = moduleRepository.deleteById(id)
}