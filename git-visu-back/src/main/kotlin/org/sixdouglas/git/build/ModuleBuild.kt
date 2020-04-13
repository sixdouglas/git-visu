package org.sixdouglas.git.build

import java.time.LocalDateTime

data class ModuleBuild(val name: String, val branch: String, val buildDate: LocalDateTime, val moduleCode: String)
