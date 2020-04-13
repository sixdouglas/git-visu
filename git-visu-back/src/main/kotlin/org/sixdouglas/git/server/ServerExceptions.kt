package org.sixdouglas.git.server

class ServerIdAutomaticallyGeneratedException: Exception("Id for servers are auto-generated. To INSERT new Server, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
