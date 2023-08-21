package taxi_together.taxi_together.bankbook.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import taxi_together.taxi_together.bankbook.domain.constant.BankbookConstant
import taxi_together.taxi_together.converter.BankConverter
import taxi_together.taxi_together.member.domain.Member

@Entity
class Bankbook private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(updatable = false) @OnDelete(action = OnDeleteAction.CASCADE) val member: Member,
    @Convert(converter = BankConverter::class) @Column(nullable = false, columnDefinition = BankbookConstant.BANK_TYPE) var bank: Bank,
    @Column(nullable = false, columnDefinition = BankbookConstant.ACCOUNT_NUM_TYPE) var accountNumber: String,
) {
    companion object {
        fun create(member: Member, bank: String, accountNumber: String) =
            Bankbook(member = member, bank = Bank.create(bank), accountNumber = accountNumber)
    }

    fun updateBankbook(bank: String, accountNumber: String) {
        this.bank = Bank.create(bank)
        this.accountNumber = accountNumber
    }
}