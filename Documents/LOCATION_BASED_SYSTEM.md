# 반경 거리내의 위도/경도 좌표 범위 구하기

## 위치 기반으로 조회
* 입력받은 위치와 데이터의 위치를 알때 조건을 걸어 조회하는 것은 너무나도 쉽다.
* 그러나 DB에 있는 수많은 데이터들의 위치를 알지 못하고, 현재 위치를 기반으로 적절한 조건의 위치에 있는 데이터를 가져와야하는 쿼리를 짜야하는 경우가 있다.
* 이런 경우 위치의 범위를 구하여, 해당 범위안에 들어오는 데이터를 조회해야한다.

## 범위 구하기
* 필자는 500m 이내에 가능한 좌표를 바탕으로 데이터를 조회하는 쿼리를 짯다.
* 반경 500m 이내에 가능한 좌표의 범위를 구하는 코드는 아래와 같다.
```kotlin
fun calculateCoordinateLatitudeRange(latitude: Double): ClosedRange<Double> {
    return (latitude - 0.0045).coerceAtLeast(-90.0)..(latitude + 0.0045).coerceAtMost(90.0)
}

fun calculateCoordinateLongitudeRange(longitude: Double): ClosedRange<Double> {
    return (longitude - 0.0045).coerceAtLeast(-180.0)..(longitude + 0.0045).coerceAtMost(180.0)
}
```

## 원하는 거리내에서 구하기
* 위의 코드는 문제가 있다. 바로 500m로 고정되는 것이다.
* 필자는 500m로 정하였지만, 500m 이내가 아니라 원하는 특정 거리가 있을때 구하는 방법은 아래와 같다.
* 500m는 0.0045이 었다. 위의 코드에서 반복되는 0.0045이라는 수가 보일 것이다.
* 위도/경도 1도는 약 111.32km에 해당하므로, 100m는 대략 (100 / 111320) = 0.0009도 정도이다.
* 1km라면 (1000 / 111320) 으로 계산하면 된다. 킬로미터 급에서 미터로 환산하여 계산한다는 것만 주의하면 된다.
* 즉 100m 반경 이내에 위도/경도 좌표 범위를 구하면 아래와 같다.
* 자주 사용하는 거리의 경우 상수를 제공해, 위에서 주의하라 하였던 킬로미터 급에서 미터로 환산하는 것 또한 안정적으로 해결할 수 있다. 
```kotlin
const val HUNDRED_METER: Int = 100
const val FIVE_HUNDRED_METER: Int = 500
const val ONE_KILOMETER: Int = 1000
const val THREE_KILOMETER: Int = 3000

private fun calculateRadiusDegree(radiusMeters: Int): Double = (radiusMeters / 111320.0)

fun calculateCoordinateLatitudeRange(latitude: Double, radiusMeters: Int): ClosedRange<Double> {
    val radiusDegree = calculateRadiusDegree(radiusMeters)
    return (latitude - radiusDegree).coerceAtLeast(-90.0)..(latitude + radiusDegree).coerceAtMost(90.0)
}

fun calculateCoordinateLongitudeRange(longitude: Double, radiusMeters: Int): ClosedRange<Double> {
    val radiusDegree = calculateRadiusDegree(radiusMeters)
    return (longitude - radiusDegree).coerceAtLeast(-180.0)..(longitude + radiusDegree).coerceAtMost(180.0)
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
            calculateCoordinateLatitudeRange(latitude, FIVE_HUNDRED_METER).start,
            calculateCoordinateLatitudeRange(latitude, FIVE_HUNDRED_METER).endInclusive
        )
}

private fun getCoordinateLongitude(longitude: Double): Range<Double> {
        return Range.closed(
            calculateCoordinateLongitudeRange(longitude, FIVE_HUNDRED_METER).start,
            calculateCoordinateLongitudeRange(longitude, FIVE_HUNDRED_METER).endInclusive
        )
}
```
* closed range를 range<double>의 형태로 바꾸어서 between 쿼리 안에 넣었다.
* jdsl은 between, and 쿼리를 사용하지 않고, between() 안에 범위를 넣어 연산한다.

## 주의점
* 앞서 설명했듯 km단위로 넘어가는 순간만 조심하면 큰 문제는 없다.
* 그러나 이렇게 구한 거리는 '반경' 거리이다.
* 반경 거리는 네비게이션에 나오는 목적지 거리와 같이 도로 기반의 거리와는 크게 다르다.
* 반경 1km라고 한다면 상당히 짧아보일 수 있는데, 좌표를 여러번 테스트 해보면서 구글맵에 좌표를 넣어 검색해보면
* 반경 1km는 상당히 큰 거리이다. 따라서 카풀과 같이 근처에 있는 사람들을 위주로 반경거리를 잡을때에는 100~500m가 적당한 길이인 것 같다.
* '반경'거리임을 잊지말고, 적당한 반경 거리를 세워서 연산하면 좋은 결과를 낳을 수 있을 것이다.