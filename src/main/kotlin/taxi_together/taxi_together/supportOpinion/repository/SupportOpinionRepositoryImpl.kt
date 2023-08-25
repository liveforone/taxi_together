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
    override fun findOneByUUID(uuid: UUID): SupportOpinion {
        return try {
            queryFactory.singleQuery {
                select(entity(SupportOpinion::class))
                from(SupportOpinion::class)
                where(col(SupportOpinion::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findOneByUUIDAndWriter(uuid: UUID, writerUUID: UUID): SupportOpinion {
        return try {
            queryFactory.singleQuery {
                select(entity(SupportOpinion::class))
                from(SupportOpinion::class)
                join(SupportOpinion::writer)
                where(col(SupportOpinion::uuid).equal(uuid).and(col(Member::uuid).equal(writerUUID)))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findOneDtoByUUID(uuid: UUID): SupportOpinionInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(SupportOpinion::uuid),
                    col(SupportOpinion::content),
                    col(SupportOpinion::supportOpinionType),
                    col(SupportOpinion::createdDatetime)
                ))
                from(SupportOpinion::class)
                where(col(SupportOpinion::uuid).equal(uuid))
            }
        } catch (e: NoResultException) {
            throw SupportOpinionException(SupportOpinionExceptionMessage.SUPPORT_OPINION_IS_NULL)
        }
    }

    override fun findAllSupportOpinions(lastUUID: UUID?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::uuid),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            where(ltLastUUID(lastUUID))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    override fun findSupportOpinionsByType(opinionType: String, lastUUID: UUID?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::uuid),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            where(col(SupportOpinion::supportOpinionType).equal(SupportOpinionType.create(opinionType)))
            where(ltLastUUID(lastUUID))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    override fun findSupportOpinionsByWriter(writerUUID: UUID, lastUUID: UUID?): List<SupportOpinionInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportOpinion::uuid),
                col(SupportOpinion::content),
                col(SupportOpinion::supportOpinionType),
                col(SupportOpinion::createdDatetime)
            ))
            from(SupportOpinion::class)
            join(SupportOpinion::writer)
            where(col(Member::uuid).equal(writerUUID))
            where(ltLastUUID(lastUUID))
            orderBy(col(SupportOpinion::id).desc())
            limit(SupportOpinionRepoConstant.PAGE_SIZE)
        }
    }

    private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(SupportOpinion::id)))
            from(SupportOpinion::class)
            where(col(SupportOpinion::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        return lastUUID?.let { and(col(SupportOpinion::id).lessThan(findLastId(it))) }
    }
}