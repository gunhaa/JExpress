# JExpress 코딩 표준

## 기본 원칙

> 가독성을 최우선으로 삼는다. (대부분의 경우 코드 그 자체가 문서의 역할을 해야 함)

> 정말 합당한 이유가 있지 않는 한, 통합개발환경(IDE)의 자동 서식을 따른다. (윈도우 IntelliJ의 "Ctrl + Alt + L" 기능)

## 참조 문서 
- [Java Code Conventions](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)
- [Google Java Style Guide](https://source.android.com/docs/setup/contribute/code-style?hl=ko)
- [Android Open Source Project Java Code Style for Contributors](https://google.github.io/styleguide/javaguide.html)
- [Pocu 아카데미](https://docs.popekim.com/ko/coding-standards/pocu-java)

## 메인 코딩 표준

1. 패키지 이름은 모두 소문자로 작성한다
```shell
 package com.awesome.math;
```
2. import를 할 때는 전체 이름을 다 적는다<br>

- 틀린 방식 :
```shell
 import com.foo.*;
```
- 맞는 방식 :
```shell
import com.foo.bar;
```
3. 클래스와 열거형을 선언할 때는 파스칼 표기법을 따른다.
```java
 public class PlayerManager {
     // ...
 }
```
4. 클래스, 멤버 변수, 메서드에는 언제나 접근 제어자를 붙인다. 단, 기본 패키지 접근 권한이 필요할 경우에는 그렇지 않는다.
```java
 public class Person {
    int mHeight; // 기본 (패키지) 접근 권한
    private int age;

    public int getAge() {
        // ...
    }

    private void doSomething() {
        // ...
    }
}
```
5. 접근 제어자는 다른 수정자(modifier)앞에 붙인다.
```java
 public static void doSomething() {
     // ...
 }
```
6. 모든 메서드 이름은 카멜 표기법을 따라 짓는다.
```java
 public int getAge() {
     // ...
 }
```
7. 지역 변수와 메서드 매개변수 이름은 카멜 표기법을 따라 짓는다.
```java
 int age = 10;

 public void someMethod(int someParameter) {
     int someNumber;
 }
```
8. 메서드 이름은 동사로 시작한다.
```java
 public int getAge() {
     // ...
 }
```
9. 단, 단순히 부울(boolean) 상태를 반환하는 메서드의 동사 부분은 최대한 is, can, has, should를 사용하되 그러는 것이 부자연스러울 경우에는 상태를 나타내는 다른 3인칭 단수형 동사를 사용한다.
```java
public boolean isAlive(Person person);
public boolean hasChild(Person person);
public boolean canAccept(Person person);
public boolean shouldDelete(Person person);
public boolean exists(Person person);
```
10. 인터페이스의 이름은 I로 시작한다.
```java
 interface IFooBar;
```
11. 열거형 멤버의 이름은 모두 대문자로 하되 밑줄로 각 단어를 분리한다.
```java
public enum MyEnum {
    FUN,
    MY_AWESOME_VALUE
}
```
12. 멤버 변수의 이름은 카멜 표기법을 따른다.
```java
public class Employee {
    public String nickName;
    protected String familyName;
    private int age;
}
```
13. 값을 반환하는 메서드의 이름은 무엇을 반환하는지 알 수 있게 짓는다.
```java
public int getAge();
```
14. 단순히 반복문에 사용되는 변수가 아닌 경우엔 i, e 같은 변수명 대신 index, employee 처럼 변수에 저장되는 데이터를 한 눈에 알아볼 수 있는 변수명을 사용한다.
```java
int i; // BAD
int a; // BAD
int index; // GOOD
int age; // GOOD
```
15. 줄임말(축약어)를 변수 및 메서드 명에 사용할 때는 기타 단어들과 동일하게 사용한다. 즉, 파스칼 표기법을 따르는 경우에는 오직 첫 번째 글자만 대문자로 바꾸며, 카멜 표기법을 따르는 경우에는 두 번째 단어부터 첫 번째 글자만 대문자로 바꾼다.
```java
int orderId;
String httpAddress;
String myHttp;
```
16. public 멤버 변수 대신 getter와 setter 메서드를 사용한다.
```java
public class Employee {
    private String name;

    public String getName();
    public String setName(String name);
}
```
17. 지역 변수를 선언할 때는 그 지역 변수를 처음 사용하는 코드와 최대한 가까이 선언하는 것을 원칙으로 한다.
18. 재귀 메서드는 이름 뒤에 Recursive를 붙인다.
```java
public void fibonacciRecursive();
```
19. 클래스는 각각 독립된 소스 파일에 있어야 한다. 단, 작은 클래스 몇 개를 한 파일 안에 같이 넣어두는 것이 상식적일 경우 예외를 허용한다.
20. 파일 이름은 대소문자까지 포함해서 반드시 클래스 이름과 일치해야 한다.
21. 비트 플래그 열거형은 이름 뒤에 Flags를 붙인다.
```java
public enum BitFlags {
    // 플래그들 생략
}
```
22. 변수 가리기(variable shadowing)는 허용하지 않는다. 이 규칙에 대한 유일한 예외는 멤버 변수와 생성자/setter 매개변수에 동일한 이름을 사용할 때이다.
23. var 키워드를 사용하지 않는다
24. 메서드의 매개변수로 null을 허용하지 않는 것을 추구한다.
25. 메서드를 오버라이딩할 때는 언제나 @Override 어노테이션을 붙인다.
26. 메서드에서 null을 반환할 때는 메서드 이름 뒤에 OrNull을 붙인다.

## 포맷팅
1. 탭(tab)은 IntelliJ의 기본 값을 사용한다. 다른 IDE를 사용할 경우에는 실제 탭 문자 대신 띄어쓰기 4칸을 사용한다.
2. if, if-else, if-else-if-else 문은 다음의 중괄호 스타일을 사용한다.
```java
 if (expression) {
     // 코드 생략
 } else if (expression) {
     // 코드 생략
 } else {
     // 코드 생략
 }
 ```
3. 중괄호 ({}) 는 새로운 줄에서 열지 않으나 닫을 때는 새로운 줄에서 닫는다. 단, 다음 항목의 예외는 허용한다.
```java
 public void myMethod() {
     while (expression) {
         // 코드 생략
     }

     try {
         // 코드 생략
     } catch (ExceptionClass ex) {
         // 코드 생략
     }
 }
```
4. 중괄호 안({})에 코드가 한 줄만 있더라도 반드시 중괄호를 사용한다.
```java
 if (!foo) {
     return;
 }
```
5. 한 줄에 변수 하나만 선언한다.