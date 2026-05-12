package com.yurin.train_tickets_mobile.data

import com.yurin.train_tickets_mobile.data.model.Booking
import com.yurin.train_tickets_mobile.data.model.BookingResponse
import com.yurin.train_tickets_mobile.data.model.BuyRequest
import com.yurin.train_tickets_mobile.data.model.BuyResponse
import com.yurin.train_tickets_mobile.data.model.Login
import com.yurin.train_tickets_mobile.data.model.LoginResponse
import com.yurin.train_tickets_mobile.data.model.SeatsResponse
import com.yurin.train_tickets_mobile.data.model.TripResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body login: Login): LoginResponse

    @GET("/api/trips")
    suspend fun getTrips(
        @Query("departure") departure: String? = null,
        @Query("destination") destination: String? = null,
    ): List<TripResponse>

    @GET("/api/trips/{trip_id}/seats")
    suspend fun getSeats(@Path("trip_id") tripId: Int): SeatsResponse

    @POST("/api/bookings")
    suspend fun bookSeat(@Body booking: Booking): BookingResponse

    @GET("/api/bookings/{booking_id}")
    suspend fun getBooking(@Path("booking_id") bookingId: Int): BookingResponse

    @POST("/api/bookings/{booking_id}/pay")
    suspend fun buyTicket(@Path("booking_id") bookingId: Int, @Body buyRequest: BuyRequest): BuyResponse

    @POST("/api/bookings/{booking_id}/cancel")
    suspend fun cancelBooking(@Path("booking_id") bookingId: Int)
}