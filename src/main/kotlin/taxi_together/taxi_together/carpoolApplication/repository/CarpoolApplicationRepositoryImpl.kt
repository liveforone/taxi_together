package taxi_together.taxi_together.carpoolApplication.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import taxi_together.taxi_together.carpoolApplication.domain.CarpoolApplication
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongCarpoolInfo
import taxi_together.taxi_together.carpoolApplication.dto.response.CarpoolApplicationBelongMemberInfo
import taxi_together.taxi_together.exception.exception.CarpoolApplicationException
import taxi_together.taxi_together.exception.message.CarpoolApplicationExceptionMessage
import java.util.*

@Repository
class CarpoolApplicationRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : CarpoolApplicationCustomRepository {
    override fun findOneByCarpoolIdAndMemberUUID(carpoolId: Long, memberUUID: UUID): CarpoolApplication {
        return try {
            queryFactory.singleQuery {
                select(entity(CarpoolApplication::class))
                from(CarpoolApplication::class)
                where(
                    col(CarpoolApplication::carpoolId).equal(carpoolId)
                        .and(col(CarpoolApplication::memberUUID).equal(memberUUID))
                )
            }
        } catch (e: NoResultException) {
            throw CarpoolApplicationException(CarpoolApplicationExceptionMessage.CARPOOL_APPLICATION_IS_NULL)
        }
    }

    override fun findCarpoolApplicationsByCarpoolId(carpoolId: Long): List<CarpoolApplicationBelongCarpoolInfo> {
        return queryFactory.listQuery {
            select(listOf(col(CarpoolApplication::memberUUID)))
            from(CarpoolApplication::class)
            where(col(CarpoolApplication::carpoolId).equal(carpoolId))
            orderBy(col(CarpoolApplication::timestamp).desc())
        }
    }

    override fun findCarpoolApplicationsByMemberUUID(memberUUID: UUID): List<CarpoolApplicationBelongMemberInfo> {
        return queryFactory.listQuery {
            select(listOf(col(CarpoolApplication::carpoolId)))
            from(CarpoolApplication::class)
            where(col(CarpoolApplication::memberUUID).equal(memberUUID))
            orderBy(col(CarpoolApplication::timestamp).desc())
        }
    }

    override fun countCarpoolApplicationByCarpoolId(carpoolId: Long): Long {
        return queryFactory.singleQuery {
            select(count(entity(CarpoolApplication::class)))
            from(CarpoolApplication::class)
            where(col(CarpoolApplication::carpoolId).equal(carpoolId))
        }
    }
}