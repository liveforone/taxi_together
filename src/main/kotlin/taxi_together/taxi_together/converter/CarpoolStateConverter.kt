package taxi_together.taxi_together.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import taxi_together.taxi_together.carpool.domain.CarpoolState

@Converter
class CarpoolStateConverter : AttributeConverter<CarpoolState, String> {
    override fun convertToDatabaseColumn(attribute: CarpoolState) = attribute.name
    override fun convertToEntityAttribute(dbData: String) = CarpoolState.valueOf(dbData)
}