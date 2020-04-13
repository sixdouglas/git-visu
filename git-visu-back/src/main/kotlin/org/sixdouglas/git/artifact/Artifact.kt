package org.sixdouglas.git.artifact

import org.springframework.data.annotation.Id

data class Artifact(@Id val id: Int?, val name: String, val moduleId: Int?)
