package org.sixdouglas.git.build

import java.time.OffsetDateTime

data class BuildDeployment(val envName: String?, val weight: Int?, val color: String?, val serverName: String?, val artifactName: String?, val port:String?, val profile: String?, val deploymentDate: OffsetDateTime?)
