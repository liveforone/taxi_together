package taxi_together.taxi_together.supportOpinion.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import taxi_together.taxi_together.converter.SupportOpinionTypeConverter
import taxi_together.taxi_together.globalUtil.DATETIME_TYPE
import taxi_together.taxi_together.globalUtil.getDatetimeDigit
import taxi_together.taxi_together.member.domain.Member
import taxi_together.taxi_together.supportOpinion.domain.constant.SupportOpinionConstant
import java.time.LocalDateTime

@Entity
class SupportOpinion private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) @OnDelete(action = OnDeleteAction.CASCADE) val writer: Member,
    @Column(nullable = false, columnDefinition = SupportOpinionConstant.CONTENT_TYPE) val content: String,
    @Convert(converter = SupportOpinionTypeConverter::class)
    @Column(
        nullable = false,
        columnDefinition = SupportOpinionConstant.SUPPORT_TYPE
    ) val supportOpinionType: SupportOpinionType,
    @Column(
        nullable = false,
        updatable = false,
        columnDefinition = DATETIME_TYPE
    ) val createdDatetime: Long = getDatetimeDigit(LocalDateTime.now())
) {
    companion object {
        fun create(writer: Member, content: String, opinionType: String) =
            SupportOpinion(
                writer = writer,
                content = content,
                supportOpinionType = SupportOpinionType.create(opinionType)
            )
    }
}