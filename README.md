# MADA Server v2.0

## 인증 어노테이션 사용법

### `@Authenticated`

로그인한 사용자만 접근 가능한 API에 붙이는 어노테이션입니다.  
내부적으로 Spring Security의 `@PreAuthorize("isAuthenticated()")`를 감싸고 있습니다.

메서드 또는 클래스 단위에 적용할 수 있습니다.

```java
// 메서드 단위
@Authenticated
@GetMapping("/me")
public ResponseEntity<AccountDto> getMyProfile(...) { ... }

// 클래스 단위 — 해당 컨트롤러의 모든 엔드포인트에 인증 적용
@Authenticated
@RestController
@RequestMapping("/api/posts")
public class PostController { ... }
```

---

### `@CurrentAccount`

인증된 사용자의 정보를 컨트롤러 파라미터로 주입받는 어노테이션입니다.  
`AccountDto`(id, nickname) 타입 파라미터에만 사용할 수 있습니다.

`@Authenticated`와 함께 사용하는 것이 일반적입니다.

```java
@Authenticated
@GetMapping("/me")
public ResponseEntity<AccountDto> getMyProfile(
    @CurrentAccount AccountDto account
) {
    return ResponseEntity.ok(account);
}
```

> 주의: 인증되지 않은 상태에서 `@CurrentAccount`를 사용하면 `UnauthorizedException`이 발생합니다.

---

## git push 전 확인 사항

**반드시 `ServerApplicationTests`를 실행한 뒤 push하세요.**

```bash
./gradlew test --tests "com.mada.server.ServerApplicationTests"
```

이 테스트는 Spring Modulith를 통해 **모듈 간 의존성 규칙 위반을 검증**하고, PlantUML 다이어그램과 문서를 자동 생성합니다.  
모듈 규칙을 어기는 코드가 있으면 이 테스트에서 실패합니다.

---

## 모듈 구조 및 참조 규칙 (Spring Modulith)

이 프로젝트는 [Spring Modulith](https://spring.io/projects/spring-modulith)를 사용하여 모듈 간 의존성을 강제합니다.

### 모듈 경계

`com.mada.server` 하위의 각 패키지(예: `account`, `auth`, `common`, `notification`)가 하나의 독립 모듈입니다.  
각 모듈의 `package-info.java`에 `@ApplicationModule`을 선언하여 등록합니다.

```
com.mada.server
├── account        ← 모듈
├── auth           ← 모듈
├── common         ← 모듈
└── notification   ← 모듈
```

### 참조 가능한 영역

모듈의 **루트 패키지에 위치한 public 타입**만 다른 모듈에서 참조할 수 있습니다.

```
account/
├── Account.java          ✅ 다른 모듈에서 참조 가능
├── AccountDto.java       ✅ 다른 모듈에서 참조 가능
├── AccountQueryService.java  ✅ 다른 모듈에서 참조 가능
└── internal/
    └── AccountRepository.java  ❌ 참조 불가 (내부 서브패키지)
```

### 모듈 내부 서브패키지는 기본적으로 참조 불가

`internal` 등 서브패키지에 위치한 클래스는 해당 모듈 내부 구현으로 취급되어, **다른 모듈에서 직접 참조할 수 없습니다.**

```java
// ❌ 다른 모듈에서 internal 패키지 직접 참조 — 모듈 규칙 위반
import com.mada.server.account.internal.AccountRepository;
```

### `@NamedInterface` — 서브패키지를 공개적으로 노출할 때

서브패키지를 다른 모듈에서 참조할 수 있게 하려면, 해당 서브패키지의 `package-info.java`에 `@NamedInterface`를 선언합니다.

```java
// common/error/package-info.java
@NamedInterface(name = "error")
package com.mada.server.common.error;

import org.springframework.modulith.NamedInterface;
```

선언 후 다른 모듈의 `@ApplicationModule`에서 해당 named interface를 명시적으로 허용해야 합니다.

```java
// 다른 모듈의 package-info.java
@ApplicationModule(allowedDependencies = "common::error")
package com.mada.server.auth;

import org.springframework.modulith.ApplicationModule;
```

이렇게 하면 `common.error` 패키지의 타입(`BusinessException`, `ErrorResponse` 등)을 `auth` 모듈에서 사용할 수 있습니다.

### 정리

| 위치 | 다른 모듈에서 참조 |
|---|---|
| 모듈 루트 패키지의 public 타입 | 가능 |
| 모듈 내 서브패키지 (`internal/` 등) | **불가** (기본) |
| `@NamedInterface` 선언된 서브패키지 | 가능 (`allowedDependencies` 설정 필요) |
