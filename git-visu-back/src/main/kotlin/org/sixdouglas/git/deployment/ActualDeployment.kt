package org.sixdouglas.git.deployment

data class ActualDeployment(val moduleName:String, val moduleCode:String, val component:String, val envName: String, val envWeight: Int, val branch: String, val version: String)
