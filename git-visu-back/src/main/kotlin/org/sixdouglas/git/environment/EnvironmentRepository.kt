package org.sixdouglas.git.environment

import org.springframework.data.repository.reactive.ReactiveSortingRepository

internal interface EnvironmentRepository : ReactiveSortingRepository<Environment, Int>