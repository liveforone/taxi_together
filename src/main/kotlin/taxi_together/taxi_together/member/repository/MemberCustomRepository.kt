package taxi_together.taxi_together.member.repository

import taxi_together.taxi_together.member.domain.Member
import taxi_together.taxi_together.member.dto.response.MemberInfo
import java.util.*

interface MemberCustomRepository {
    fun findIdByEmailNullableForValidate(email: String): Long?
    fun findOneByEmail(email: String): Member
    fun findOneByUUID(uuid: UUID): Member
    fun findOneDtoByUUID(uuid: UUID): MemberInfo
}