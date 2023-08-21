package taxi_together.taxi_together.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import taxi_together.taxi_together.member.domain.Role

@Converter
class RoleConverter : AttributeConverter<Role, String> {
    override fun convertToDatabaseColumn(attribute: Role) = attribute.name

    override fun convertToEntityAttribute(dbData: String) = Role.valueOf(dbData)
}