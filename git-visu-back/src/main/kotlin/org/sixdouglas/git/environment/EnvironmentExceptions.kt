package org.sixdouglas.git.environment

class EnvironmentIdAutomaticallyGeneratedException: Exception("Id for environments are auto-generated. To INSERT new Environment, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
