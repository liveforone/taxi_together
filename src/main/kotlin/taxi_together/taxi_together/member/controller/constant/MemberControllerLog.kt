package taxi_together.taxi_together.member.controller.constant

enum class MemberControllerLog(val log: String) {
    SIGNUP_SUCCESS("회원가입 성공"),
    LOGIN_SUCCESS("로그인 성공"),
    UPDATE_EMAIL_SUCCESS("이메일 변경 성공"),
    UPDATE_PW_SUCCESS("비밀번호 변경 성공"),
    WITHDRAW_SUCCESS("회원탈퇴 성공")
}