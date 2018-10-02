# html5  
- 오디오, 비디오태그  

- 입력양식 태그: form, input~~  

- 공간분할태그  
div, span  

시멘틱 태그  
> 시멘틱 웹: 특정 태그에 의미 부여, 웹에서 데이터를 효율적으로 추출!  
> 이게바로 시멘틱 웹이다  
> 대표적인 시멘택 태그: header, nav, section, aside, article, footer...  

-------------------------------------

# CSS  
## css 적용방법
- cascade: 상속받는다!!  
  - 오버라이딩 된다--> 구체적이게 들어가서 스타일 지정한거 우선 적용  
- html에 적용방법 3가지  
  - inline방법  
    - html문서 내의 태그에 style속성 안에 작성  
    - 권고하지 않는 방식..!->동적인거에 주로 적용  
  - embeded방법  
    - head안에 style태그로 css선언  
    - style태그안에 선택자(selector)마다 적용  
    - 현재문서에서만 사용  
  - external방법  
    - 외부파일 css생성  
    - 생성한 외부 파일을 link태그로 지정(rel, type, href속성 지정)  
  - css지시자 @import사용  
    - style태그 안에 css지시자 @import url("css파일 경로"); 적용  
    - 외부 익스터널이랑 비슷  
    - 여러 css적용할때 쓰기 좋음  
    
  ## css선택자  
  - !important: 가장 높은 스타일 우선순위를 가진다  
  - selector  
    - *: 전체선택자. 페이지 모든 요소에 적용    
    - 태그선택자: 문서안에 특정 태그에 스타일 적용  
    - class, id선택자  
      - class선택자: 태그에 상관없이 공통적으로 적용하고 싶을때 grouping시켜줌  
        > .(클래스 명)  
      - id선택자: 유일한 값, id속성에 값 할당    
        > #(id값)  
    - 하위, 자식선택자  
      - 하위선택자: (상위요소) (하위요소) 모든 하위요소에 스타일 적용  
      - 자식선택자: 부모요소 > 자식요소  자식요소에만 스타일 적용  
    - 인접형제선택자, 형제선택자  
      - 인접형제: 같은부모가진 형제--> +로 씀..뒤에 나온 형제에만 스타일 적용  
      - 일반형제: 모든 형제에 스타일 적용--> ~로 표현  
    - 열거형 선택자: 쉼표로 선택자 구분  
    - 속성선택자(필터 선택자): 태그의 속성에 접근  
      > [속성]: 지정한 속성 갖고있는 모든 요소 스타일 적용  
      > [속성 *= 값]: 지정한 값의 일부만 포함된 속성에 스타일 지정  
      > ^ 시작, $ 끝, ~ 여러값중 하나만 일치(무슨말?????????공백 사이 값이들어있는거..?), | 정확하게 일치하거나 -로 이어지는거  
    - 가상클래스 선택자: 주로 a태그에서 많이 사용... 예제 보면서 이해하기  
    
    ## css 텍스트 관련  
    - 글꼴(font관련)  
      - em: 현재 폰트 대비 사이즈  
    - text관련  
      - text-align  
      - text-shadow: 그래픽처리느낌 내줌. 속성값 되게 많음  
      - text-decoration: 밑줄이나 취소줄  
      뭐 많ㅇ느데 필요할때 찾아써...  
      text-indent(들여쓰기), text-transform(대소문자), line-height(줄간격조정), white-space(공백처리)
    - 목록스타일  
      - list-style-type: 머리기호정해주는거   
      - list-style-position: 들여쓰기 설정    
      - list-style-image: 머리기호 대신 이미지    
    - 색상과 배경관련  
      - ppt랑 예제보면서 공부...  
    
    ## 레이아웃관련 스타일  
    ### 박스모델  
    - 너비높이  
    - margin(top right bottom left) (top-bottom right-left) -->element간 사이  
      > * 마진에 margin: 0 auto;(위아래 0, 왼오 auto-->가운데로해줌!)속성 주면 가운데정렬
    - padding(top right bottom left)  
    - border(width color sytle)  
    - display속성  
      - block inline inline-block none(영역자체가 안보임) inherit(상위요소의 display속성 상속)
    - position속성  
      - static: 기본 상태(inline은 인라인으로 블럭은블럭으로)  
      - absolute: 좌상단 좌표기준으로(left top)  
      - relative: static일때의 기준점이 기준점이됨...?기준점바뀜..  
      - fixed: 화면에 고정 
    - overflow속성  
      - scroll hidden auto  
    - visibility속성  
      - hidden(공간은 차지함)  
    - z-index속성: 큰값이 위로 쌓임  
    - float속성  
      - 엘리먼트를 떠있게  
      - 전파가돼...?ㅠㅠ 그래서 clear속성을 줘야함  
    - 표관련 속성..  
    - cursor속성  
    
    
      
        
  

    
    
