요구사항

1. HTTP Header
```
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```
---
## 힌트
#### 1. 
```
> InputStream을 한 줄 단위로 일기 위해 BufferReader를 생성한다.
> BufferedReader.readLine() 메소드를 활용해 라인별로 HTTP요청 정보를 읽는다.
> HTTP 요청 정보 전체를 출력한다.
   헤더 마지막은 while(!"".equals(line)){} 로 확인 가능하다.
   line이 null 값인 경우에 대한 예외 처리도 해야 한다. 그렇지 않을 경우 무한 루프에 빠진다.
   에 빠진다. if(line ==null) {return;}
```
#### 2.
```
> HTTP 요청 정보의 첫번째 라인에서 요청 URL( / ) 을 추출한다.
>   String[] tokens = line.split(" ");를 활용해 문자열을 분리할수 있다.
> 구현은 별로의 유틸 클래스를 만들고 단위 테스트를 만들어 진행하면 편하다.
```
#### 3.
```
> 요청 URL에 해당하는 파일을 webapp 디렉토리에서 읽어 잔달하면 된다.
> 파일 데이터를 byte[]로 읽는다.
  byte[] bytes = Files.readAllByTes(new File("./webapp" + url).toPath());
```


