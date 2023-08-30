# DB 설계

## ER-Diagram
![image](https://github.com/liveforone/taxi_together/assets/88976237/c4374d4b-3773-48ce-80a9-7674ef077b17)

## 테이블 생성 및 제약조건 명시
### 회원 -> member
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
### 신고상태 -> reportState
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
### 계좌 -> bankbook
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
### 카풀 -> carpool
```sql
create table carpool (
      individual_fare integer not null,
      pickup_latitude float(53) not null,
      pickup_longitude float(53) not null,
      id bigint not null auto_increment,
      member_id bigint,
      pickup_date BIGINT(12) not null,
      carpool_state VARCHAR(11) not null,
      destination varchar(255) not null,
      primary key (id),
      foreign key (member_id) references on Member (id) on delete cascade
);
CREATE INDEX carpool_coordinate_idx ON carpool (carpool_state, pickup_date, pickup_latitude, pickup_longitude);
```
### 카풀 신청 -> carpool_application
```sql
create table carpool_application (
      timestamp integer,
      carpool_uuid binary(16) not null,
      member_uuid binary(16) not null,
      primary key (carpool_uuid, member_uuid)
);
CREATE INDEX carpool_application_timestamp_idx ON carpool_application (timestamp);
```
### 지원 의견 -> support_opinion
```sql
create table support_opinion (
      created_datetime BIGINT(12) not null,
      id bigint not null auto_increment,
      writer_id bigint,
      content VARCHAR(300) not null,
      support_opinion_type VARCHAR(8) not null,
      primary key (id),
      foreign key (writer_id) references member (id) on delete cascade
);
CREATE INDEX support_opinion_type_idx ON support_opinion (support_opinion_type);
```
### 지원 의견 답변 -> support_answer
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

## no-offset 페이징
* 페이징 성능을 향상하기 위해 no-offset 방식으로 페이징 처리한다.
* 이에 따라 동적쿼리 구성이 필요하다.
* 아래는 jdsl로 구성한 no-offset 동적쿼리이다.
* 현재 정렬은 desc이기 때문에 asc를 사용한다면 lessThan을 greaterThan으로 변경한다.
* 정책은 lastId가 null 일경우 첫 페이지로 인식하고
* 그 이외에는 lastId보다 작은 id에 한해 조회한다.
```kotlin
private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastId: Long?): PredicateSpec? {
        return lastId?.let { and(col(엔티티::id).lessThan(it)) }
}
```

## 복합키 주의 사항
* 복합키는 인덱스의 순서를 반드시 주의하여한다.
* 이는 쿼리를 짤때 큰 성능 차이를 유발할 수 있다.