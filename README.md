# Taxi Together - 택시 투게더

## 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 설계](#2-프로젝트-설계)
3. [고민점](#3-고민점)

## 1. 프로젝트 소개
### 소개
### 기술 스택
* Framework : Spring Boot 3.1.2
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

## 3. 고민점
### 기술적 고민
* [대소문자 구분없이 ENUM 생성](https://github.com/liveforone/taxi_together/blob/master/Documents/IGNORE_CASE_IN_ENUM.md)
* [위도 경도 좌표의 적절 데이터 타입(decimal이 만능이 아니다!)](https://github.com/liveforone/taxi_together/blob/master/Documents/LOCATION_DATA_TYPE.md)
* [위치의 범위를 구하여, 범위안에 들어오는 데이터 조회하기](https://github.com/liveforone/taxi_together/blob/master/Documents/LOCATION_BASED_SYSTEM.md)
### 비즈니스적 고민