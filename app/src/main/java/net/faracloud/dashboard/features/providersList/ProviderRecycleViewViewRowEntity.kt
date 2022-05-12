package net.faracloud.dashboard.features.providersList

data class ProviderRecycleViewViewRowEntity(
    var identifier: String,
    var title: String,
    var authorizationToken: String,
    var description: String,
    var creationDate: String,
    var updatedDate: String,
    var contact: String,
    var contactEmail: String,
    var enable: Boolean,
)

