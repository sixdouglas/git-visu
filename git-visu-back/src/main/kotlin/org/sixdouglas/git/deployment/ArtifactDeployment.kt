package org.sixdouglas.git.deployment

import java.time.LocalDateTime

data class ArtifactDeployment(val moduleCode: String, val buildName: String, val buildBranch: String, val artifactName: String, val serverName: String, val deploymentDate: LocalDateTime)
