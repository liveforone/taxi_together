package taxi_together.taxi_together.supportAnswer.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import taxi_together.taxi_together.globalUtil.DATETIME_TYPE
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.supportAnswer.domain.constant.SupportAnswerConstant
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinion
import java.time.LocalDateTime

@Entity
class SupportAnswer private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) @OnDelete(action = OnDeleteAction.CASCADE) val supportOpinion: SupportOpinion,
    @Column(nullable = false, columnDefinition = SupportAnswerConstant.CONTENT_TYPE) val content: String,
    @Column(
        nullable = false,
        updatable = false,
        columnDefinition = DATETIME_TYPE
    ) val createdDatetime: Long = getDatetimeDigit(LocalDateTime.now())
) {
    companion object {
        fun create(supportOpinion: SupportOpinion, content: String) =
            SupportAnswer(supportOpinion = supportOpinion, content = content)
    }
}