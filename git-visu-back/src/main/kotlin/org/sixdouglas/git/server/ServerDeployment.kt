package org.sixdouglas.git.server

import java.time.OffsetDateTime

data class ServerDeployment(val serverId: Int,
                            val moduleId: Int, val moduleName: String, val moduleCode: String,
                            val artifactId: Int, val artifactName: String,
                            val installationId: Int?, val port: String?, val profile: String?,
                            val deploymentId: Int?, val deploymentDate: OffsetDateTime?,
                            val buildId: Int?, val buildName: String?, val branch: String?, val snapshot: Boolean?, val buildDate: OffsetDateTime?
)
