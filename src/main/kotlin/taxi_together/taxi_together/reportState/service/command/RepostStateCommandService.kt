package taxi_together.taxi_together.reportState.service.command

import taxi_together.taxi_together.reportState.domain.ReportState
import taxi_together.taxi_together.reportState.dto.request.ReportMember
import taxi_together.taxi_together.reportState.service.command.log.ReportLog
import taxi_together.taxi_together.reportState.repository.RepostStateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import taxi_together.taxi_together.logger
import taxi_together.taxi_together.member.domain.Member

@Service
@Transactional
class RepostStateCommandService @Autowired constructor(
    private val repostStateRepository: RepostStateRepository
) {
    fun createRepostState(member: Member) {
        repostStateRepository.save(ReportState.create(member))
    }

    fun releaseSuspend(email: String): ReportState {
        return repostStateRepository.findOneByMemberEmail(email)
            .also { it.releaseSuspend() }
    }

    fun addRepost(reportMember: ReportMember) {
        with(reportMember) {
            repostStateRepository.findOneByMemberUUID(memberUUID!!)
                .also { it.addReport() }
            logger().info(ReportLog.REPORT_MEMBER.log + memberUUID)
        }
    }
}