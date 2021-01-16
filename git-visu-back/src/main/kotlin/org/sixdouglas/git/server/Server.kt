package org.sixdouglas.git.server

import org.springframework.data.annotation.Id

data class Server (@Id val id: Int?, val name: String, val fullName: String, val role: Array<String>, val environmentId: Int?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Server

        if (id != other.id) return false
        if (name != other.name) return false
        if (fullName != other.fullName) return false
        if (!role.contentEquals(other.role)) return false
        if (environmentId != other.environmentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + role.contentHashCode()
        result = 31 * result + (environmentId ?: 0)
        return result
    }
}