# 계좌 정보 설계

## 상세 설계
* 계좌 정보는 카풀 등록자가 금전적인 것을 부담하는 정책에 따라, 카풀 등록자에게 개인별로 지급해야할 금액을 송금할 수 있도록 하는 정보입니다.
* 즉 카풀 등록자의 계좌 정보가 담겨있습니다.
* 시중 15개의 은행을 선택 가능하도록 설계/구현 되었습니다.
* 특히나 은행 선택은, 프론트엔드에게 대/소문자 구분에 대한 강력한 제한을 없애기 위해 대소문자 구분없이 입력가능하도록 세팅하였습니다.
* 은행과 계좌 번호는 변경 가능합니다. 계좌번호는 최대 20자로 지정하여, 국내 모든 은행의 계좌번호를 입력가능하도록 하였습니다.

## API 설계
```
[GET] /bankbook/info/{memberUUID}
[PUT] /bankbook/update/info
```

## Json Body 예시
```json
[UpdateBankbook]
{
  "memberUUID": "de262e08-661a-4c1d-ae3e-24aa293e4eab",
  "bank": "nh",
  "accountNumber": "test_9db277f82952"
}
```

## DB 설계
```sql
create table bankbook (
     id bigint not null auto_increment,
     member_id bigint,
     account_number VARCHAR(20) not null,
     bank VARCHAR(11) not null,
     primary key (id),
     foreign key (member_id) references on Member (id) on delete cascade
);
```