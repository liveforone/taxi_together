package taxi_together.taxi_together.supportAnswer.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import taxi_together.taxi_together.exception.exception.SupportAnswerException
import taxi_together.taxi_together.exception.message.SupportAnswerExceptionMessage
import taxi_together.taxi_together.supportAnswer.domain.SupportAnswer
import taxi_together.taxi_together.supportAnswer.dto.response.SupportAnswerInfo
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion

@Repository
class SupportAnswerRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : SupportAnswerCustomRepository {
    override fun findOneById(id: Long): SupportAnswer {
        return try {
            queryFactory.singleQuery {
                select(entity(SupportAnswer::class))
                from(SupportAnswer::class)
                where(col(SupportAnswer::id).equal(id))
            }
        } catch (e: NoResultException) {
            throw SupportAnswerException(SupportAnswerExceptionMessage.ANSWER_IS_NULL)
        }
    }

    override fun findAnswersBySupportOpinion(supportOpinionId: Long): List<SupportAnswerInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(SupportAnswer::id),
                col(SupportAnswer::content),
                col(SupportAnswer::createdDatetime)
            ))
            from(SupportAnswer::class)
            where(nestedCol(col(SupportAnswer::supportOpinion), SupportOpinion::id).equal(supportOpinionId))
            orderBy(col(SupportAnswer::id).desc())
        }
    }
}