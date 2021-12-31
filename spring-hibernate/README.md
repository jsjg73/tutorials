# Hibernate5 with Spring [D003]

- This project is a practice field for Hibernate with Spring.
- Basically, referring to [Baeldung's guides](https://www.baeldung.com/)

## [1. Hibernate: save, persist, update, merge, saveOrUpdate](https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate)

- ***1.1 영속성 문맥 구현체로서 Session.***
    - Session은 데이터 저장하는 메소드가 여러개 있다.(persist, save, update, merge, saveOrUpdate). 이 차이를 익히기 위해선 두가지(a),(b)를 먼저 알아야한다.
    - (a). Session의 역할.
    - (b). states of entity instances in relation to the Session.

    
    - ***1.1.1 Managing Entity Instances***
        - session은 논리적 트랜잭션.
        - persistence context의 database를 사용할땐, 모든 record에 대해 single instance여야한다.
        - Hibernate에선 persistence context가 Session이다.
        - JPA표준명세에선 EntityManager다
        - Session이 좀 더 풍부한 인터페이스다.

    - ***1.1.2 States of Entity Instances***
        - 어떤 entity의 인스턴스든지, session과 관계에서 3개의 주요 상태 중 하나다.
        - transient, persistent, detached.

        - entity instance가 persistent 상태이면, session이 flushing일때 instance의 값들이 대응되는 DB의 records로 전달된다.
    
- ***1.2 오퍼레이션 간의 차이***
    - 메소드(persist, save 등..)를 호출할 때 바로 쿼리가 실행되지 않는다. 실제로 DB에 데이터가 전달되는 것은 트랜잭션이나 세션이 완료될 때다.