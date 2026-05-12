package com.yurin.train_tickets_mobile.domain.entity

import com.yurin.train_tickets_mobile.R

enum class SeatCategory(val value: Int) {
    ECONOMY(R.string.economy),
    STANDARD(R.string.standard),
    BUSINESS(R.string.business),
    LUXURY(R.string.luxury)
}