# 카풀 설계

## 상세 설계
* 택시를 탑승하는 위치의 위도/경도 좌표를 저장한다.
* 카풀을 하는 시간을 사용하여, 카풀 신청시 validation한다.
* 개인별 택시비 정산은, 카풀이 완료 된 후 진행된다.
* 총 택시비를 입력하면, 카풀 신청의 count쿼리 결과를 바탕으로 나누어서 개인별 택시비를 정산한다.
* 카풀은 등록자를 제외하고 3명이 신청가능하다.
* 카풀 삭제는 카풀을 신청한 사람이 한 명도 없을시에 가능하다.
* 신청 가능 카풀을 조회를 할때에, 현재 위도, 경도 좌표를 입력받아 반경 3km이내에 있는 카풀을 리턴한다.

## API 설계
```
[GET] /carpool/detail/{uuid}
[GET] /carpool/search
[POST] /carpool/create
[PUT] /carpool/calculate
[DELETE] /carpool/remove
```

## Json Body 예시
```json
[CreateCarpool]
{
  "memberUUID": "d2c70c4d-f0f1-4f99-9ea5-332a9c7addcf",
  "pickupLatitude": 37.494461,
  "pickupLongitude": 127.029592,
  "month": 9,
  "day": 18,
  "hour": 18,
  "minute": 36,
  "destination": "잠실역"
}

[CalculateCarpool]
{
  "uuid": "7f40e542-f878-41a1-a820-9bae8da38985",
  "totalFare": 4900
}

[RemoveCarpool]
{
  "uuid": "3a5db143-4a68-4c7b-b04d-3b157ca983a8",
  "memberUUID": "d2c70c4d-f0f1-4f99-9ea5-332a9c7addcf"
}
```

## DB 설계
```sql
create table carpool (
      individual_fare integer not null,
      pickup_latitude float(53) not null,
      pickup_longitude float(53) not null,
      id bigint not null auto_increment,
      member_id bigint,
      pickup_date BIGINT(12) not null,
      uuid BINARY(16) not null UNIQUE,
      carpool_state VARCHAR(11) not null,
      destination varchar(255) not null,
      primary key (id),
      foreign key (member_id) references on Member (id) on delete cascade
);
CREATE INDEX uuid_idx ON carpool (uuid);
```