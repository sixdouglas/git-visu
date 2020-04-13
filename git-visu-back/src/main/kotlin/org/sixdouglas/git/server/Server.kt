package org.sixdouglas.git.server

import org.springframework.data.annotation.Id

data class Server (@Id val id: Int?, val name: String, val fullName: String, val role: ServerRole, val environmentId: Int?)