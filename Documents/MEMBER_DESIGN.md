# 회원 설계

## 회원관리 기술
* 세션보다 가볍고 관리하기 편한 Jwt 토큰을 활용해 서버에 부담을 최대한 줄이는 방식을 채택하였습니다.
* 토큰관리는 가장 보편적인 방식인 프론트엔드의 로컬스토리지에서 jwt토큰을 관리하는것을 전제로 합니다.
* 토큰의 만료시간은 2시간 입니다.

## 상세 설계
* 외부 식별자 이름은 uuid로 합니다. 다른 도메인에서 사용하게된다면 memberUUID 형태로 사용합니다.
* 회원은 member, admin 두 종류가 있습니다.
* 비밀번호는 모두 bcrypt로 암호화 합니다.
* 회원의 이메일과 비밀번호는 변경 가능합니다.
* 로그인은 시큐리티에 위임합니다
* 정지된 계정인지는 로그인시에 판별합니다.
* 정규화를 하여 회원의 상태와 신고 로직은 ReportState 테이블로 이관하였습니다.

## API 설계
```
[POST] /member/signup
[POST] /member/login
[GET] /member/info
[PUT] /member/{uuid}/update/email
[PUT] /member/{uuid}/update/password
[DELETE] /member/{uuid}/withdraw
```

## Json body 예시
```json
[SignupRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234",
  "nickName": "testName",
  "bank": "nh",
  "accountNumber": "3411155800301"
}

[LoginRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234"
}

[UpdateEmail]
{
  "newEmail": "new_email@gmail.com"
}

[UpdatePassword]
{
  "password": "1111",
  "oldPassword": "1234"
}

[WithdrawRequest]
{
  "pw": "112233"
}
```

## DB 설계
```sql
create table member (
    id bigint not null auto_increment,
    uuid BINARY(16) not null UNIQUE,
    email varchar(255) not null,
    password varchar(100) not null,
    auth varchar(7) not null,
    nick_name VARCHAR(10) not null UNIQUE,
    primary key (id)
);
CREATE INDEX uuid_idx ON member (uuid);
CREATE INDEX email_idx ON member (email);
```