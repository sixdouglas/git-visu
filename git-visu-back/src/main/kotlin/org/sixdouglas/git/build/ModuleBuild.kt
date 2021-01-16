package org.sixdouglas.git.build

import java.time.OffsetDateTime

data class ModuleBuild(val name: String, val branch: String, val buildDate: OffsetDateTime, val moduleCode: String)
