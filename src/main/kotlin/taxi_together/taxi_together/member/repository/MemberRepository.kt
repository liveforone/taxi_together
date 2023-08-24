package taxi_together.taxi_together.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import taxi_together.taxi_together.member.domain.Member

interface MemberRepository : JpaRepository<Member, Long>, MemberCustomRepository