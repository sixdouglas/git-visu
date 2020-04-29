package org.sixdouglas.git.build

import org.sixdouglas.git.module.IModuleComponent
import org.sixdouglas.git.module.Module
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
internal class BuildComponent internal constructor(internal val buildRepository: BuildRepository
                                                   , internal val moduleComponent: IModuleComponent) : IBuildComponent {

    override fun addBuild(moduleBuild: ModuleBuild) : Mono<Build> {
        return moduleComponent.findModuleByCode(moduleBuild.moduleCode)
                .switchIfEmpty( moduleComponent.save(Module(null, moduleBuild.moduleCode, moduleBuild.moduleCode)))
                .flatMap { module -> buildRepository.save(Build(null, moduleBuild.name, moduleBuild.branch, moduleBuild.buildDate, module.id)) }
    }

    override fun getBuilds() = buildRepository.findAll()

    override fun updateBuild(id: Int, build: Build): Mono<Build> {
        val buildToBeSaved = Build(id, build.name, build.branch, build.buildDate, build.moduleId)
        return buildRepository.save(buildToBeSaved)
    }

    override fun deleteBuild(id: Int) = buildRepository.deleteById(id)
    override fun findBuildsByModuleId(id: Int) = buildRepository.findBuildsByModuleId(id)
    override fun findBuildByNameAndModuleId(buildName: String, moduleId: Int) = buildRepository.findBuildByNameAndModuleId(buildName, moduleId)
    override fun save(build: Build) = buildRepository.save(build)

}