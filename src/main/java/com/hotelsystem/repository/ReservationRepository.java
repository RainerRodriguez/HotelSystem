package com.hotelsystem.repository;

import com.hotelsystem.model.Reservation;
import com.hotelsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM Reservation r
    WHERE r.room.id = :roomId
    AND r.checkIn < :checkOut
    AND r.checkOut > :checkIn
""")

    boolean existsByRoomAndDatesOverlap(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );
}
