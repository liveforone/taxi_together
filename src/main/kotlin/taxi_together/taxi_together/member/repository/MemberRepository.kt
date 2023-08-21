package taxi_together.taxi_together.member.repository

import taxi_together.taxi_together.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.member.repository.MemberCustomRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository