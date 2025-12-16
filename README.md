# Movie & Drama Compose App

Jetpack Compose를 사용하여 제작한 영화/드라마 콘텐츠 소개 안드로이드 앱입니다.

## 앱 소개
앱 실행 시 영화 또는 드라마를 선택할 수 있으며,
각 콘텐츠 목록과 상세 정보를 확인할 수 있습니다.
Compose UI와 Navigation을 활용한 화면 전환 구조를 이해하고 구현하는 것을 목표로 제작했습니다.

## 주요 기능
- 영화 / 드라마 선택 화면
- 콘텐츠 목록 화면 (LazyColumn)
- 상세 화면 (이미지, 명대사, 정보 표시)
- 화면 전환 시 애니메이션 효과 적용

## 사용 기술
- Kotlin
- Jetpack Compose
- Material3
- Navigation Compose

## 제작 과정
1. Jetpack Compose 기반 Empty Activity 프로젝트 생성
2. 영화/드라마 데이터를 Content 클래스로 정의
3. Navigation Compose로 화면 흐름 구성
4. LazyColumn을 사용해 리스트 UI 구현
5. 상세 화면에 이미지와 애니메이션 효과 추가

## 느낀 점
Jetpack Compose를 사용하면서 XML 없이도 UI를 직관적으로 구성할 수 있다는 점을 이해할 수 있었고,
Navigation과 상태 관리의 중요성을 실습을 통해 배울 수 있었습니다.
