package com.olayg.onlykats.model.request

import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.Order
import com.olayg.onlykats.util.PageAction

// TODO: 9/10/21 Add more possible queries
data class Queries(
    var endPoint: EndPoint?,
    var limit: Int,
    var page: Int?,
    var order: Order = Order.ASC
)