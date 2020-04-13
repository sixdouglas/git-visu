package org.sixdouglas.git.environment

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface EnvironmentRepository : ReactiveCrudRepository<Environment, Int>