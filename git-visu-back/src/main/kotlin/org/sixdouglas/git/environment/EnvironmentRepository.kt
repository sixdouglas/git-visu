package org.sixdouglas.git.environment

import org.springframework.data.repository.reactive.ReactiveCrudRepository

internal interface EnvironmentRepository : ReactiveCrudRepository<Environment, Int>