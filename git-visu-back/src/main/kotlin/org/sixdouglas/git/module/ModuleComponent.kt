package org.sixdouglas.git.module

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
internal class ModuleComponent internal constructor(internal val moduleRepository: ModuleRepository) : IModuleComponent {

    override fun addModule(module: Module) : Mono<Module> {
        return if (module.id != null) {
            Mono.error(ModuleIdAutomaticallyGeneratedException())
        } else {
            moduleRepository.save(module)
        }
    }

    override fun getModules() = moduleRepository.findAll()

    override fun updateModule(id: Int, module: Module): Mono<Module> {
        val moduleToBeSaved = Module(id, module.name, module.code)
        return moduleRepository.save(moduleToBeSaved)
    }

    override fun deleteModule(id: Int) = moduleRepository.deleteById(id)
    override fun findModuleByCode(moduleCode: String) = moduleRepository.findModuleByCode(moduleCode)
    override fun save(module: Module) = moduleRepository.save(module)
}