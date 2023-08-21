package taxi_together.taxi_together.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import taxi_together.taxi_together.reportState.domain.MemberState

@Converter
class MemberStateConverter : AttributeConverter<MemberState, String> {
    override fun convertToDatabaseColumn(attribute: MemberState) = attribute.name
    override fun convertToEntityAttribute(dbData: String) = MemberState.valueOf(dbData)
}