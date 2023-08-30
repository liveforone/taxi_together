package taxi_together.taxi_together.carpool.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import org.springframework.stereotype.Repository
import taxi_together.taxi_together.carpool.domain.Carpool
import taxi_together.taxi_together.carpool.domain.CarpoolState
import taxi_together.taxi_together.carpool.dto.response.CarpoolInfo
import taxi_together.taxi_together.carpool.repository.constant.CarpoolRepoConstant
import taxi_together.taxi_together.exception.exception.CarpoolException
import taxi_together.taxi_together.exception.message.CarpoolExceptionMessage
import taxi_together.taxi_together.globalUtil.*
import taxi_together.taxi_together.member.domain.Member
import java.time.LocalDateTime

@Repository
class CarpoolRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : CarpoolCustomRepository {
    override fun findOneById(id: Long): Carpool {
        return try {
            queryFactory.singleQuery {
                select(entity(Carpool::class))
                from(Carpool::class)
                where(col(Carpool::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw CarpoolException(CarpoolExceptionMessage.CARPOOL_IS_NULL)
        }
    }

    override fun findOneDtoById(id: Long): CarpoolInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Carpool::id),
                    col(Member::uuid),
                    col(Carpool::pickupLatitude),
                    col(Carpool::pickupLongitude),
                    col(Carpool::pickupDate),
                    col(Carpool::destination),
                    col(Carpool::individualFare)
                ))
                from(Carpool::class)
                join(Carpool::member)
                where(col(Carpool::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw CarpoolException(CarpoolExceptionMessage.CARPOOL_IS_NULL)
        }
    }

    override fun findCarpools(currLatitude: Double, currLongitude: Double, lastId: Long?): List<CarpoolInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Carpool::id),
                col(Member::uuid),
                col(Carpool::pickupLatitude),
                col(Carpool::pickupLongitude),
                col(Carpool::pickupDate),
                col(Carpool::destination),
                col(Carpool::individualFare)
            ))
            from(Carpool::class)
            join(Carpool::member)
            where(
                col(Carpool::carpoolState).equal(CarpoolState.UNCOMPLETED)
                    .and(col(Carpool::pickupDate).greaterThan(getDatetimeDigit(LocalDateTime.now())))
                    .and(col(Carpool::pickupLatitude).between(getCoordinateLatitude(currLatitude)))
                    .and(col(Carpool::pickupLongitude).between(getCoordinateLongitude(currLongitude)))
            )
            where(ltLastId(lastId))
            orderBy(col(Carpool::id).desc())
            limit(CarpoolRepoConstant.LIMIT_SIZE)
        }
    }

    private fun getCoordinateLatitude(latitude: Double): Range<Double> {
        return Range.closed(
            calculateCoordinateLatitudeRange(latitude, FIVE_HUNDRED_METER).start,
            calculateCoordinateLatitudeRange(latitude, FIVE_HUNDRED_METER).endInclusive
        )
    }

    private fun getCoordinateLongitude(longitude: Double): Range<Double> {
        return Range.closed(
            calculateCoordinateLongitudeRange(longitude, FIVE_HUNDRED_METER).start,
            calculateCoordinateLongitudeRange(longitude, FIVE_HUNDRED_METER).endInclusive
        )
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long?): PredicateSpec? {
        return lastId?.let { and(col(Carpool::id).lessThan(it)) }
    }
}