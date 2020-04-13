package org.sixdouglas.git.module

class ModuleIdAutomaticallyGeneratedException: Exception("Id for modules are auto-generated. To INSERT new Module, do not supply any id. To UPDATE existing one use PUT HTTP Verb.")
