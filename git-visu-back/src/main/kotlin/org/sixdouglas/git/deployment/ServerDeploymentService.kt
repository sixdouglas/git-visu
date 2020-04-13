package org.sixdouglas.git.deployment

import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ServerDeploymentService(val client: DatabaseClient) {
    val query = "select srv.id server_id, " +
                      " mod.id module_id, mod.name module_name, " +
                      " art.id artifact_id, art.name artifact_name, " +
                      " ins.id installation_id, ins.port, ins.profile, " +
                      " dep.id deployment_id, dep.deployment_date, " +
                      " bui.id build_id, bui.name build_name, bui.branch, bui.build_date " +
            "from server srv " +
                " inner join installation ins on ins.server_id = srv.id " +
                " inner join artifact art on art.id = ins.artifact_id " +
                " inner join module mod on mod.id = art.module_id " +
                " left outer join deployment dep on dep.installation_id = ins.id " +
                " left outer join build bui on bui.id = dep.build_id and bui.module_id = mod.id " +
            "where srv.id = :serverId " +
            "order by dep.deployment_date desc"

    fun findLatestDeploymentsForServer(serverId: Int): Flux<ServerDeployment> {
        return client
                .execute(query)
                .bind("serverId", serverId)
                .`as`(ServerDeployment::class.java)
                        .fetch()
                        .all()
    }
}