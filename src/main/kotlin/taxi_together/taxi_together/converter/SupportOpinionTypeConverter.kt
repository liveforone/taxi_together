package taxi_together.taxi_together.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import taxi_together.taxi_together.supportOpinion.domain.SupportOpinionType

@Converter
class SupportOpinionTypeConverter : AttributeConverter<SupportOpinionType, String> {
    override fun convertToDatabaseColumn(attribute: SupportOpinionType) = attribute.name

    override fun convertToEntityAttribute(dbData: String) = SupportOpinionType.valueOf(dbData)
}