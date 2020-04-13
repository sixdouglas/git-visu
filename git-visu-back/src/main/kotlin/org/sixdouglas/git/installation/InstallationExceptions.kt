package org.sixdouglas.git.installation

class InstallationIdAutomaticallyGeneratedException: Exception("Id for installations are auto-generated. To INSERT new Installation, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
