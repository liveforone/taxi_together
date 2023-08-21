package taxi_together.taxi_together.bankbook.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import jakarta.persistence.NoResultException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import taxi_together.taxi_together.bankbook.domain.Bankbook
import taxi_together.taxi_together.bankbook.dto.response.BankbookInfo
import taxi_together.taxi_together.exception.exception.BankbookException
import taxi_together.taxi_together.exception.message.BankbookExceptionMessage
import taxi_together.taxi_together.member.domain.Member
import java.util.*

@Repository
class BankbookRepositoryImpl @Autowired constructor(
    private val queryFactory: SpringDataQueryFactory
) : BankbookCustomRepository {
    override fun findOneByMemberUUID(memberUUID: UUID): Bankbook {
        return try {
            queryFactory.singleQuery {
                select(entity(Bankbook::class))
                from(Bankbook::class)
                join(Bankbook::member)
                where(col(Member::uuid).equal(memberUUID))
            }
        } catch (e: NoResultException) {
            throw BankbookException(BankbookExceptionMessage.BANKBOOK_IS_NULL)
        }
    }

    override fun findOneDtoByMemberUUID(memberUUID: UUID): BankbookInfo {
        return try {
            queryFactory.singleQuery {
                select(listOf(
                    col(Bankbook::bank),
                    col(Bankbook::accountNumber)
                ))
                from(Bankbook::class)
                join(Bankbook::member)
                where(col(Member::uuid).equal(memberUUID))
            }
        } catch (e: NoResultException) {
            throw BankbookException(BankbookExceptionMessage.BANKBOOK_IS_NULL)
        }
    }
}