package org.sixdouglas.git.build

class BuildIdAutomaticallyGeneratedException: Exception("Id for builds are auto-generated. To INSERT new Build, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
