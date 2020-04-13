package org.sixdouglas.git.installation

import org.springframework.data.annotation.Id

data class Installation (@Id val id: Int?, val artifactId: Int, val serverId: Int, val port: Int?, val profile: String?)