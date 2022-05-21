package net.faracloud.dashboard.features.map.data

import java.util.*

data class ComponentRepoModel(var name: String,
                              var type : String,
                              var publicAccess: Boolean,
                              var createdAt: Date,
                              var updatedAt : Date,
                              var enable: Boolean)
