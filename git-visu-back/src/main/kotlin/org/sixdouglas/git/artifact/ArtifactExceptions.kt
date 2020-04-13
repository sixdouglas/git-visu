package org.sixdouglas.git.artifact

class ArtifactIdAutomaticallyGeneratedException: Exception("Id for artifacts are auto-generated. To INSERT new Artifact, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
