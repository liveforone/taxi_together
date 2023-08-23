# 대소문자 구분없이 ENUM 생성

## enum 생성방법
* enum을 정적 팩토리 메서드를 이용해 생성하게 되면 기본적으로 동일한 값으로 입력받아야 생성된다.
* 즉 Kim 이라는 enum이 있다면 Kim을 입력받아야, Kim이 생성된다.
* 필자는 은행정보를 클라이언트로 입력받는 설계를 하였다.
* 그러나 위의 enum 생성전략을 따르게 되면 클라이언트에게 너무나 큰 제약을 안겨주게된다.
* 대소문자가 크게 상관없는 상황인데 말이다.
* 이에 따라 아래와 같은 해답을 제시한다.

## 대소문자 구분없이 enum 생성하기
* 전제는 다음과 같다. 문자열을 입력받는다. -> 대소문자 구분없이 생성한다.
* 이것을 진행하려면 enum에서 같은 값을 찾아야한다. 같은 값을 찾을때 대소문자 구분없이 찾도록 한다.
* 그리고 나서 같은 값이 없으면 예외를 발생시키고, 있다면 생성한다.
```kotlin
companion object {
        fun create(bank: String): Bank =
            entries.find { it.name.equals(bank, ignoreCase = true) } ?: throw BankbookException(BankbookExceptionMessage.NOT_EXIST_BANK)
}
```
* entries.find {} 메서드를 코틀린에서 제공하여준다.
* 이를 통해 enum에서 원하는 값을 찾을 수 있다.
* {} 안에는 조건이 들어간다. enum의 name과 equal한 값을 찾으면된다.
* 이때 대소문자 구분을 해제시켜준다. 바로 ignoreCase = true 로 지정하여서 말이다.
* find함수는 값이 없으면 null을 리턴한다. 이때 이 null 값을 받게되면 ?: 를 이용해 예외를 처리하여주면 된다.