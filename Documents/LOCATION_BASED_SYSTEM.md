# 위치의 범위를 구하여, 범위안에 들어오는 데이터 조회하기

## 위치 기반으로 조회
* 입력받은 위치와 데이터의 위치를 알때 조건을 걸어 조회하는 것은 너무나도 쉽다.
* 그러나 DB에 있는 수많은 데이터들의 위치를 알지 못하고, 현재 위치를 기반으로 적절한 조건의 위치에 있는 데이터를 가져와야하는 쿼리를 짜야하는 경우가 있다.
* 이런 경우 위치의 범위를 구하여, 해당 범위안에 들어오는 데이터를 조회해야한다.

## 범위 구하기
* 필자는 3km이내에 가능한 좌표를 바탕으로 데이터를 조회하는 쿼리를 짯다.
* 반경 3km이내에 가능한 좌표의 범위를 구하는 코드는 아래와 같다.
```kotlin
fun calculateCoordinateLatitudeRange(latitude: Double): ClosedRange<Double> {
    return (latitude - 0.027).coerceAtLeast(-90.0)..(latitude + 0.027).coerceAtMost(90.0)
}

fun calculateCoordinateLongitudeRange(longitude: Double): ClosedRange<Double> {
    return (longitude - 0.027).coerceAtLeast(-180.0)..(longitude + 0.027).coerceAtMost(180.0)
}
```

## 쿼리 짜기
* 이렇게 구한 범위를 바탕으로 쿼리를 아래와 같이짤 수 있다.
* jdsl로 구현한 쿼리이다.
```kotlin
override fun findCarpools(currLatitude: Double, currLongitude: Double, lastUUID: UUID?): List<CarpoolInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Carpool::uuid),
                col(Member::uuid),
                col(Carpool::pickupLatitude),
                col(Carpool::pickupLongitude),
                col(Carpool::pickupDate),
                col(Carpool::destination),
                col(Carpool::individualFare)
            ))
            from(Carpool::class)
            join(Carpool::member)
            where(
                col(Carpool::carpoolState).equal(CarpoolState.UNCOMPLETED)
                    .and(col(Carpool::pickupDate).greaterThan(getDatetimeDigit(LocalDateTime.now())))
                    .and(col(Carpool::pickupLatitude).between(getCoordinateLatitude(currLatitude)))
                    .and(col(Carpool::pickupLongitude).between(getCoordinateLongitude(currLongitude)))
            )
            where(ltLastUUID(lastUUID))
            orderBy(col(Carpool::id).desc())
            limit(CarpoolRepoConstant.LIMIT_SIZE)
        }
}

private fun getCoordinateLatitude(latitude: Double): Range<Double> {
        return Range.closed(
            calculateCoordinateLatitudeRange(latitude).start,
            calculateCoordinateLatitudeRange(latitude).endInclusive
        )
}

private fun getCoordinateLongitude(longitude: Double): Range<Double> {
        return Range.closed(
            calculateCoordinateLongitudeRange(longitude).start,
            calculateCoordinateLongitudeRange(longitude).endInclusive
        )
}
```
* closed range를 range<double>의 형태로 바꾸어서 between 쿼리 안에 넣었다.
* jdsl은 between, and 쿼리를 사용하지 않고, between() 안에 범위를 넣어 연산한다.