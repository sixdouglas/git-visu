package org.sixdouglas.git.artifact

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
internal class ArtifactComponent internal constructor(internal val artifactRepository: ArtifactRepository) : IArtifactComponent {

    override fun addArtifact(artifact: Artifact) : Mono<Artifact> {
        if (artifact.id != null) {
            return Mono.error(ArtifactIdAutomaticallyGeneratedException())
        } else {
            return artifactRepository.save(artifact)
        }
    }

    override fun getArtifacts() = artifactRepository.findAll()

    override fun updateArtifact(id: Int, artifact: Artifact): Mono<Artifact> {
        val artifactToBeSaved = Artifact(id, artifact.name, artifact.moduleId, artifact.role)
        return artifactRepository.save(artifactToBeSaved)
    }

    override fun deleteArtifact(id: Int) = artifactRepository.deleteById(id)
    override fun findArtifactsByModuleId(id: Int) = artifactRepository.findArtifactsByModuleId(id)
    override fun findArtifactByName(artifactName: String) = artifactRepository.findArtifactByName(artifactName)
}