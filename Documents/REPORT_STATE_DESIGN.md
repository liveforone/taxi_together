# 신고상태 설계

## 상세 설계
* 신고 상태는, 기존에 회원에서 관리하던 신고 상태를 정규화하여 분리해낸 테이블입니다.
* 정지 기간이 있는 회원의 신고를 다루기 위해서는 최소 3개의 컬럼이 필요하기에, 분리하여내었습니다.
* 회원 가입시 자동으로 생성됩니다.
* 이 테이블은 uuid를 두지 않습니다. 외부에 id/uuid를 노출할 일이 전혀없고, 조회시에는 오로지 회원의 uuid로만 찾기 때문입니다.
* 이에 따라 id만 존재하는 유일한 테이블입니다.
* 신고는 어드민과 일반 회원 모두 가능합니다.
* 클라이언트에서는 각 게시물, 댓글, 대댓글에 api를 배치하면됩니다.
* 모든 게시글, 댓글, 대댓글에는 작성자의 uuid가 포함되기 때문에 이를 이용해 신고처리합니다.
* 신고는 3건 이상마다 한 달, 6달, 영구 정지 순으로 이어집니다.
* 누적 신고횟수는 삭제되지 않으며, 지속적인 신고가 이루어질 경우 영구 정지까지 가능합니다.
* 신고 기능에 대한 설명은 타 프로젝트에서 정립한 [신고 기능 문서](https://github.com/liveforone/howru/blob/master/Documents/HOW_TO_SUSPEND_USER.md)를 따로 만들었으니, 해당 문서에서 확인 바랍니다.

## API 설계
```
[GET] /report/info/{memberUUID}
[POST] /report/member
```

## Json body 예시
```json
[ReportMember]
{
  "memberUUID": "f79e091c-2069-4153-ae07-12d8bd27d2de"
}
```

## DB 설계
```sql
create table report_state (
     id bigint not null auto_increment,
     member_id bigint,
     modified_state_date INT(8) not null,
     report_count integer not null,
     member_state VARCHAR(17) not null,
     primary key (id),
     foreign key (member_id) references on Member (id) on delete cascade
);
```