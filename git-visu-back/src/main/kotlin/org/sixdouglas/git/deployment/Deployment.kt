package org.sixdouglas.git.deployment

import org.springframework.data.annotation.Id
import java.time.OffsetDateTime

data class Deployment(@Id val id:Int?, val buildId: Int, val installationId: Int, val deploymentDate: OffsetDateTime)
