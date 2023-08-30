# 지원 의견(support) 답변 설계

## 상세 설계
* 지원의견에 대한 답변을 저장하는 테이블입니다.
* 어드민만 작성/삭제가 가능합니다. 이에 따라 command 작업시에 지속적으로 어드민인지 권한을 체크합니다.
* 지원의견 답변은 수정이 불가능합니다. 또한 여러개의 답변을 달 수 있습니다.
* 지원 의견에 속한 답변들을 모두 컬렉션으로 리턴하는 api한개와 생성, 삭제에 대한 api 총 3개를 지원합니다.

## API 설계
```
[GET] /support/answer/{supportOpinionId}
[POST] /support/answer/create
[DELETE] /support/answer/remove
```

## Json Body 예시
```json
[CreateSupportAnswer]
{
  "memberUUID": "31ebc254-7cab-48d2-b26e-d013c9edcf87",
  "supportOpinionId": 1,
  "content": "신고를 9번당해야 영구정지됩니다."
}

[RemoveSupportAnswer]
{
  "id": 1,
  "memberUUID": "31ebc254-7cab-48d2-b26e-d013c9edcf87"
}
```

## DB 설계
```sql
create table support_answer (
      created_datetime BIGINT(12) not null,
      id bigint not null auto_increment,
      support_opinion_id bigint,
      content VARCHAR(300) not null,
      primary key (id),
      foreign key (support_opinion_id) references support_opinion (id) on delete cascade
);
```