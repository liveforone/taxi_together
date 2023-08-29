# Taxi Together - 택시 투게더

## 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 설계](#2-프로젝트-설계)
3. [고민점](#3-고민점)

## 1. 프로젝트 소개
### 소개
* 위치 기반으로 동작하는 택시 합승 서비스 입니다.
* 원하는 목적지까지 사람들과 택시를 합승하여, 불필요한 택시비를 아끼고, 편안한 이동을 하려는 아이디어에서 제작하게 되었습니다.
* 카풀과 카풀 신청은 모두 위치 기반으로 동작합니다. 카풀 신청의 경우 현재 위도/경도 좌표를 바탕으로 반경 3km이내의 카풀을 조회하여 신청할 수 있습니다.
* 반경 거리 이내의 위도/경도 좌표의 범위를 구하는 알고리즘과 쿼리를 자세하게 살펴보실 수 있습니다.
* 시간, 카풀 상태, 위치, 목적지 등 다양한 조건이 한 번에 맞물려 복잡한 where절을 만들어냅니다.
* 이러한 높은 복잡도 때문에, 사용자에게 상당히 친화적인 서비스가 만들어졌습니다.
* 카풀 조회도, 카풀을 하는 사람들간의 계좌와 같은 정보공유, 회원 신고, 지원(cs)허브 등 다양한 사용자 친화적인 기능이 녹아있습니다.
* 사용자는 복잡한 클릭, api 이동을 하지 않고, 적은 수의 클릭과 api 이동을 통해 모든 일을 빠르게 처리할 수 있습니다.
* 단순하고 빠르게 사용하는 것이 대중교통 합승서비스의 핵심이라 생각해 이러한 로직을 만들어 내었습니다. 이는 상당부분 필자의 경험에 의존하였습니다.
* 코드를 살펴보시면 복잡한 로직과 함께 사용자 친화적인 설계를 엿보실 수 있습니다.
* 더욱 효과적인 대중교통 서비스를 만들어내기 위한 비즈니스적 고민이 문서에 많이 담겨있습니다.
### 기술 스택
* Framework : Spring Boot 3.1.3
* Lang : Kotlin 1.9.0, Jvm17
* Data : Spring Data Jpa & Kotlin-JDSL & MySql
* Security : Spring Security & Jwt
* Test : Junit5

## 2. 프로젝트 설계
### 시스템 설계
* [전체 설계]()
* [DB 설계]()
### 엔티티 설계
* [회원 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/MEMBER_DESIGN.md)
* [회원 신고 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/REPORT_STATE_DESIGN.md)
* [계좌 정보 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/BANKBOOK_DESIGN.md)
* [카풀 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/CARPOOL_DESIGN.md)
* [카풀 신청 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/CARPOOL_APPLICATION_DESIGN.md)
* [지원 의견(support) 설계](https://github.com/liveforone/taxi_together/blob/master/Documents/SUPPORT_OPINION_DESIGN.md)
* [지원 의견(support) 답변 설계]()

## 3. 고민점
### 기술적 고민
* [대소문자 구분없이 ENUM 생성](https://github.com/liveforone/taxi_together/blob/master/Documents/IGNORE_CASE_IN_ENUM.md)
* [위도 경도 좌표의 적절 데이터 타입(decimal이 만능이 아니다!)](https://github.com/liveforone/taxi_together/blob/master/Documents/LOCATION_DATA_TYPE.md)
* [반경 거리내의 위도/경도 좌표 범위 구하기](https://github.com/liveforone/taxi_together/blob/master/Documents/LOCATION_BASED_SYSTEM.md)
### 비즈니스적 고민
* [사용자 친화적인 로직과 빠른 카풀이 핵심이다.](https://github.com/liveforone/taxi_together/blob/master/Documents/CORE_OF_CARPOOL.md)