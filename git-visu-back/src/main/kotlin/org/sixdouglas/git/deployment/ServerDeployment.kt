package org.sixdouglas.git.deployment

import java.time.LocalDateTime

data class ServerDeployment(val serverId: Int,
                            val moduleId: Int, val moduleName: String,
                            val artifactId: Int, val artifactName: String,
                            val installationId: Int?, val port: String?, val profile: String?,
                            val deploymentId: Int?, val deploymentDate: LocalDateTime?,
                            val buildId: Int?, val buildName: String?, val branch: String?, val buildDate: LocalDateTime?
)
