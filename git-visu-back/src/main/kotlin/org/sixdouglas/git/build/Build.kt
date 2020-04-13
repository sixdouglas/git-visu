package org.sixdouglas.git.build

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Build(@Id val id: Int?, val name: String, val branch: String, val buildDate: LocalDateTime, val moduleId: Int?)
