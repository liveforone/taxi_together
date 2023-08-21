package taxi_together.taxi_together.member.service.query

import taxi_together.taxi_together.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class MemberQueryService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun getMemberByUUID(uuid: UUID) = memberRepository.findOneDtoByUUID(uuid)
}