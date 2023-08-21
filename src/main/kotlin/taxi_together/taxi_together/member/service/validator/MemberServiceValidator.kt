package taxi_together.taxi_together.member.service.validator

import taxi_together.taxi_together.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import taxi_together.taxi_together.exception.exception.MemberException
import taxi_together.taxi_together.exception.message.MemberExceptionMessage

@Component
class MemberServiceValidator @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun validateDuplicateEmail(email: String) {
        require (memberRepository.findIdByEmailNullableForValidate(email) == null) {
            throw MemberException(MemberExceptionMessage.DUPLICATE_EMAIL)
        }
    }
}