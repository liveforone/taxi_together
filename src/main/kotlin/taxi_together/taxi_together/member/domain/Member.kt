package taxi_together.taxi_together.member.domain

import taxi_together.taxi_together.member.domain.constant.MemberConstant
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import taxi_together.taxi_together.converter.RoleConverter
import taxi_together.taxi_together.exception.exception.MemberException
import taxi_together.taxi_together.exception.message.MemberExceptionMessage
import taxi_together.taxi_together.globalUtil.UUID_TYPE
import taxi_together.taxi_together.globalUtil.createUUID
import taxi_together.taxi_together.globalUtil.encodePassword
import taxi_together.taxi_together.globalUtil.isMatchPassword
import java.util.*

@Entity
class Member private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(columnDefinition = UUID_TYPE, unique = true, nullable = false) val uuid: UUID = createUUID(),
    @Convert(converter = RoleConverter::class) @Column(
        nullable = false,
        columnDefinition = MemberConstant.ROLE_TYPE
    ) var auth: Role,
    @Column(nullable = false) var email: String,
    @Column(nullable = false, columnDefinition = MemberConstant.PW_TYPE) var pw: String,
    @Column(nullable = false, unique = true, columnDefinition = MemberConstant.NICKNAME_TYPE) val nickName: String,
) : UserDetails {
    companion object {
        private fun findFitAuth(email: String) = if (email == MemberConstant.ADMIN_EMAIL) Role.ADMIN else Role.MEMBER

        fun create(email: String, pw: String, nickName: String): Member {
            return Member(
                auth = findFitAuth(email),
                email = email,
                pw = encodePassword(pw),
                nickName = nickName
            )
        }
    }

    fun isAdmin() = auth == Role.ADMIN

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePw(newPassword: String, oldPassword: String) {
        require (isMatchPassword(oldPassword, pw)) { throw MemberException(MemberExceptionMessage.WRONG_PASSWORD) }
        pw = encodePassword(newPassword)
    }


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        arrayListOf<GrantedAuthority>(SimpleGrantedAuthority(auth.auth))
    override fun getUsername() = uuid.toString()
    override fun getPassword() = pw
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}