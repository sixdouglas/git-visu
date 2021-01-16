package org.sixdouglas.git.deployment

data class DeploymentInfo(var moduleId: Int, var buildId: Int, var serverId: Int, var artifactId: Int, val role: String?, var installationId: Int)