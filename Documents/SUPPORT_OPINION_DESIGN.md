# 지원 의견(support) 설계

## 상세 설계
* 지원 의견의 종류는 총 세가지가 있다. 피드백, QnA, 요구사항(신고 처리 등)
* 사용자는 지원 의견을 등록할 수 있으며, 전체 지원의견 조회
* 상세 조회, 회원이 등록한 지원의견 조회, 지원의견 타입으로 조회 가 가능하다.
* 또한 삭제가 가능하며, 지원의견의 내용은 수정이 불가능한것이 원칙이다.

## API 설계
```
[GET] /support/opinion/detail/{uuid}
[GET] /support/opinion/all
[GET] /support/opinion/type/{opinionType}
[GET] /support/opinion/writer/{writerUUID}
[POST] /support/opinion/create
[DELETE] /support/opinion/remove
```

## Json Body 설계
```json
[CreateSupportOpinion]
{
  "writerUUID": "82602415-ca61-4601-a167-91929ec9e959",
  "content": "신고가 몇번 들어가야 정지 되나요?",
  "opinionType": "qna"
}

[RemoveSupportOpinion]
{
  "uuid": "09e683b5-78b5-43c9-9934-2b72277b0490",
  "writerUUID": "82602415-ca61-4601-a167-91929ec9e959"
}
```

## DB 설계
```sql
create table support_opinion (
      created_datetime BIGINT(12) not null,
      id bigint not null auto_increment,
      writer_id bigint,
      uuid BINARY(16) not null UNIQUE,
      content VARCHAR(300) not null,
      support_opinion_type VARCHAR(8) not null,
      primary key (id),
      foreign key (writer_id) references member (id) on delete cascade
);
CREATE INDEX uuid_idx ON support_opinion (uuid);
```