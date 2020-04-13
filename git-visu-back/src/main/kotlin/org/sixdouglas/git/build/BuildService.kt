package org.sixdouglas.git.build

import org.sixdouglas.git.module.Module
import org.sixdouglas.git.module.ModuleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BuildService (val buildRepository: BuildRepository, val moduleRepository: ModuleRepository) {

    fun addBuild(moduleBuild: ModuleBuild) : Mono<Build> {
        return moduleRepository.findModuleByCode(moduleBuild.moduleCode)
                .switchIfEmpty( moduleRepository.save(Module(null, moduleBuild.moduleCode, moduleBuild.moduleCode)))
                .flatMap { module -> buildRepository.save(Build(null, moduleBuild.name, moduleBuild.branch, moduleBuild.buildDate, module.id)) }
    }

    fun getBuilds() = buildRepository.findAll()

    fun updateBuild(id: Int, build: Build): Mono<Build> {
        val buildToBeSaved = Build(id, build.name, build.branch, build.buildDate, build.moduleId)
        return buildRepository.save(buildToBeSaved)
    }

    fun deleteBuild(id: Int) = buildRepository.deleteById(id)

}