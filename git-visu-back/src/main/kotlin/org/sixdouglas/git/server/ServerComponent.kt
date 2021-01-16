package org.sixdouglas.git.server

import org.springframework.data.domain.Sort
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.OffsetDateTime

@Service
internal class ServerComponent internal constructor (internal val serverRepository: ServerRepository
                                , internal val client: DatabaseClient) : IServerComponent {

    val serverDeploymentsQuery = "select srv.id server_id, " +
                " mod.id module_id, mod.name module_name, mod.code module_code, " +
                " art.id artifact_id, art.name artifact_name, " +
                " ins.id installation_id, ins.port, ins.profile, " +
                " dep.id deployment_id, dep.deployment_date, " +
                " bui.id build_id, bui.name build_name, bui.branch, bui.snapshot, bui.build_date " +
            "from server srv " +
                " inner join installation ins on ins.server_id = srv.id " +
                " inner join artifact art on art.id = ins.artifact_id " +
                " inner join module mod on mod.id = art.module_id " +
                " left outer join deployment dep on " +
                        " dep.installation_id = ins.id " +
                    " and dep.id = ( " +
                        " select max(dep_1.id) " +
                        " from installation ins_1 " +
                        " inner join deployment dep_1 on dep_1.installation_id = ins_1.id " +
                        " where ins.id = ins_1.id " +
                    " ) " +
                " left outer join build bui on bui.id = dep.build_id and bui.module_id = mod.id " +
            "where srv.id = :serverId " +
            "order by dep.deployment_date desc"

    override fun addServer(server: Server) : Mono<Server> {
        if (server.id != null) {
            return Mono.error(ServerIdAutomaticallyGeneratedException())
        } else {
            return serverRepository.save(server)
        }
    }

    override fun updateServer(id: Int, server: Server): Mono<Server> {
        val serverToBeSaved = Server(id, server.name, server.fullName, server.role, server.environmentId)
        return serverRepository.save(serverToBeSaved)
    }

    override fun getServerDeployments(id: Int): Flux<ServerDeployment> {
        return client
            .sql(serverDeploymentsQuery)
            .bind("serverId", id)
            .map{row -> ServerDeployment(
                row.get("server_id") as Int,
                row.get("module_id") as Int,
                row.get("module_name") as String,
                row.get("module_code") as String,
                row.get("artifact_id") as Int,
                row.get("artifact_name") as String,
                row.get("installation_id") as? Int,
                row.get("port") as? String,
                row.get("profile") as? String,
                row.get("deployment_id") as? Int,
                row.get("deployment_date") as? OffsetDateTime,
                row.get("build_id") as? Int,
                row.get("build_name") as? String,
                row.get("branch") as? String,
                row.get("snapshot") as? Boolean ?: true,
                row.get("build_date") as? OffsetDateTime
            ) }
            .all()
    }

    override fun getServers() = serverRepository.findAll()
    override fun deleteServer(id: Int) = serverRepository.deleteById(id)
    override fun getServer(id: Int) = serverRepository.findById(id)
    override fun save(server: Server) = serverRepository.save(server)
    override fun findServersByEnvironmentId(id: Int) = serverRepository.findServersByEnvironmentId(id, Sort.by("role"))
    override fun findServerByNameAndRole(serverName: String, serverRole: String): Mono<Server> = serverRepository.findServerByNameAndRole(serverName, serverRole)
}