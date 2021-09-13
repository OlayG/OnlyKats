package com.olayg.onlykats.model.request

import com.olayg.onlykats.util.EndPoint

// TODO: 9/10/21 Add more possible queries
data class Queries(
    var endPoint: EndPoint?,
    var limit: Int,
    var page: Int?,
)