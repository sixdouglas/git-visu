package org.sixdouglas.git.build

import java.time.OffsetDateTime

data class Environment (val name: String?, val weight: Int?, val color: String?, val servers: List<Server>?)
data class Server(val name: String?, val artifacts: List<Artifact>?)
data class Artifact(val name: String?, val deployment: Deployment?)
data class Deployment(val port:String?, val profile: String?, val dateTime: OffsetDateTime?)
