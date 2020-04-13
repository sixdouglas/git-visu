package org.sixdouglas.git.deployment

class DeploymentIdAutomaticallyGeneratedException: Exception("Id for deployments are auto-generated. To INSERT new Deployment, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
class DeploymentNotFoundException(override val message: String): Exception(message)
