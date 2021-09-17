package com.olayg.onlykats.model.request

import com.olayg.onlykats.util.EndPoint

data class Queries(
    var endPoint: EndPoint?,
    var limit: Int,
    var page: Int?,
)