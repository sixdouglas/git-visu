package org.sixdouglas.git.deployment

import java.time.OffsetDateTime

data class ArtifactDeployment(val moduleCode: String, val buildName: String, val buildBranch: String, val artifactName: String, val serverName: String, val deploymentDate: OffsetDateTime)
