package taxi_together.taxi_together.carpool.repository

import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.carpool.domain.Carpool

interface CarpoolRepository : JpaRepository<Carpool, Long>, CarpoolCustomRepository