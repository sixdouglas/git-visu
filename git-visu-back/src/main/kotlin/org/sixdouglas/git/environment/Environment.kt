package org.sixdouglas.git.environment

import org.springframework.data.annotation.Id

data class Environment (@Id val id: Int?, val name: String, val color: String?, val code: String, val role: String, val weight: Int)