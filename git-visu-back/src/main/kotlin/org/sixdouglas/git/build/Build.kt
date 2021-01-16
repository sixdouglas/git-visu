package org.sixdouglas.git.build

import org.springframework.data.annotation.Id
import java.time.OffsetDateTime

data class Build(@Id val id: Int?, val name: String, val branch: String, val buildDate: OffsetDateTime, val moduleId: Int?, val snapshot: Boolean = false)
