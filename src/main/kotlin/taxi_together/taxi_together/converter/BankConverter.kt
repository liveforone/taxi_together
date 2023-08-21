package taxi_together.taxi_together.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import taxi_together.taxi_together.bankbook.domain.Bank

@Converter
class BankConverter : AttributeConverter<Bank, String> {
    override fun convertToDatabaseColumn(attribute: Bank) = attribute.name
    override fun convertToEntityAttribute(dbData: String) = Bank.create(dbData)
}