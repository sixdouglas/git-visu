package org.sixdouglas.git.module

import org.springframework.data.annotation.Id

data class Module (@Id val id: Int?, val name: String, val code: String)
