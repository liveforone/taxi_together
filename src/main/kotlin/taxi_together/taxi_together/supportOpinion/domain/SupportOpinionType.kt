package taxi_together.taxi_together.supportOpinion.domain

import taxi_together.taxi_together.exception.exception.SupportOpinionException
import taxi_together.taxi_together.exception.message.SupportOpinionExceptionMessage

enum class SupportOpinionType {
    FEEDBACK, QNA, DEMAND;

    companion object {
        fun create(opinionType: String): SupportOpinionType =
            entries.find { it.name.equals(opinionType, ignoreCase = true) } ?: throw SupportOpinionException(
                SupportOpinionExceptionMessage.NO_VALID_TYPE_EXIST
            )
    }
}