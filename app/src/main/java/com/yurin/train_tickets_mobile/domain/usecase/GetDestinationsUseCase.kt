package com.yurin.train_tickets_mobile.domain.usecase

import com.yurin.train_tickets_mobile.data.SearchRepository
import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(): List<String> {
        return searchRepository.getDestinations()
    }
}