package taxi_together.taxi_together.supportOpinion.repository

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import taxi_together.taxi_together.exception.exception.SupportOpinionException
import taxi_together.taxi_together.exception.message.SupportOpinionExceptionMessage
import taxi_together.taxi_together.member.domain.Member
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinionType
import taxi_together.taxi_together.supportOpinion.dto.response.SupportOpinionInfo
import taxi_together.taxi_together.supportOpinion.repository.constant.SupportOpinionRepoConstant
import java.util.*

@Repository
class SupportOpinionRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : SupportOpinionCustomRepository {
    override fun findOneById(id: Long): SupportOpinion {
        return try {
            queryFactory.singleQuery {
                select(entity(SupportOpinion::class))
                from(SupportOpinion::class)
                where(col(SupportOpinion::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findOneByIdAndWriter(id: Long, writerUUID: UUID): SupportOpinion {
        return try {
            queryFactory.singleQuery {
                select(entity(SupportOpinion::class))
                from(SupportOpinion::class)
                join(SupportOpinion::writer)
                where(col(SupportOpinion::id).equal(id).and(col(Member::uuid).equal(writerUUID)))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findOneDtoById(id: Long): SupportOpinionInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(SupportOpinion::id),
                    col(SupportOpinion::content),
                    col(SupportOpinion::supportOpinionType),
                    col(SupportOpinion::createdDatetime)
                ))
                from(SupportOpinion::class)
                where(col(SupportOpinion::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findAllSupportOpinions(lastId: Long?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::id),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            where(ltLastId(lastId))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    override fun findSupportOpinionsByType(opinionType: String, lastId: Long?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::id),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            where(col(SupportOpinion::supportOpinionType).equal(SupportOpinionType.create(opinionType)))
            where(ltLastId(lastId))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    override fun findSupportOpinionsByWriter(writerUUID: UUID, lastId: Long?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::id),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            join(SupportOpinion::writer)
            where(col(Member::uuid).equal(writerUUID))
            where(ltLastId(lastId))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastId(lastId: Long?): PredicateSpec? {
        return lastId?.let { and(col(SupportOpinion::id).lessThan(it)) }
    }
}