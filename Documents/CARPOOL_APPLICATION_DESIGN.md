# 카풀 신청 설계

## 상세 설계
* 카풀의 id와 회원의 uuid를 모두 id로, 즉 복합키를 갖습니다.
* 정렬을 위해 unix의 timestamp를 사용하였습니다. 이는 필자의 프로젝트 중 복합키를 사용하는 엔티티를 정렬하기 위해 자주 사용되는 방식입니다.
* 카풀을 신청하려면 카풀이 마감되지 않은 상태여야하며, 
* 택시 탑승 시간이 현재의 시간보다 미래여야합니다.
* 마지막으로 최대 승차 인원인 카풀 등록자를 제외한 3명 이하여야 신청이 가능합니다.
* 이러한 조건은 CarpoolApplicationValidator에서 validation하고 있습니다.
* 카풀을 취소하려면 마지막 조건을 제외한 상태인, 마감이 되지 않은 상태 + 탑승시간이 현재 시간보다 미래인 경우만 가능합니다.
* 카풀 신청은 카풀에 속한 카풀 신청, 회원에 속한 카풀 신청, 총 두가지 형태로 조회 가능합니다.

## API 설계
```
[GET] /carpool-application/belong/carpool/{carpoolId}
[GET] /carpool-application/belong/member/{memberUUID}
[POST] /carpool-application/create
[DELETE] /carpool-application/cancel
```

## Json Body 예시
```json
[CreateCarpoolApplication]
{
  "carpoolId": 1,
  "memberUUID": "afc0f64b-53fd-43aa-8a08-817287d8f6c5"
}

[CancelCarpoolApplication]
{
  "carpoolId": 1,
  "memberUUID": "afc0f64b-53fd-43aa-8a08-817287d8f6c5"
}
```

## DB 설계
```sql
create table carpool_application (
      timestamp integer,
      carpool_uuid binary(16) not null,
      member_uuid binary(16) not null,
      primary key (carpool_uuid, member_uuid)
);
CREATE INDEX carpool_application_timestamp_idx ON carpool_application (timestamp);
```