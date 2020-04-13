package org.sixdouglas.git.artifact

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ArtifactService(val artifactRepository: ArtifactRepository) {

    fun addArtifact(artifact: Artifact) : Mono<Artifact> {
        if (artifact.id != null) {
            return Mono.error(ArtifactIdAutomaticallyGeneratedException())
        } else {
            return artifactRepository.save(artifact)
        }
    }

    fun getArtifacts() = artifactRepository.findAll()

    fun updateArtifact(id: Int, artifact: Artifact): Mono<Artifact> {
        val artifactToBeSaved = Artifact(id, artifact.name, artifact.moduleId)
        return artifactRepository.save(artifactToBeSaved)
    }

    fun deleteArtifact(id: Int) = artifactRepository.deleteById(id)
}