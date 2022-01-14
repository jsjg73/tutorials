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
    - persist 메소드는 detaced를 저장할 수 없다.
    - save 메소드는 식별자를 반환하며, detached에 대해서 새로운 record를 생성하기때문에 중복이 우려된다.(hibernate 오리지널)
    - merge 메소드는 detaced 상태를 인자로 받아서 같은 ID의 새 object를 반환한다. 인자로 받은 detached 엔티티의 상태값을 사용한다.
    - update 메소드는 detached 엔티티를 persistent상태로 변경한다. transient상태일경우는 에러 발생한다.
    - saveOrUpdate 메소드는 transient일때도 동작한다.

---

## [2. Hibernate Entity Lifecycle](https://www.baeldung.com/hibernate-entity-lifecycle)

- 2.1 Persistence Context
    - 저장소와 코드 사이에 있는 staging 영역.
    - Unit of Work 패턴.(new, drity, clean, deleted, commit, rollback)
    - Hibernate의 Session은 Persistence context 컨셉을 따른다.
    - **Hibernate entity lifecycle 상태**는 엔티티가 persistence context와 어떤 관계를 맺는지 나타낸다.

- 2.2 Managed Entity
    - whenEntityLoaded_thenEntityManaged()
    - 세션이 종료될때, 관리되는 엔티티 중에서 dirty 엔티티를 찾고 데이터베이스와 동기화한다.
- 2.3 Detached Entity
- 2.4 Transient Entity
- 2.5 Deleted Entity

- baeldung의 HibernateLifecycleUnitTest는 Spring을 사용하지 않기 때문에, Session을 사용하기 위한 환경설정을 직접 진행한다. 스프링 환경에서 동일한 테스트를 수행하도록 변경해본다.

---

## [3. One-to-One Relationship](https://www.baeldung.com/jpa-one-to-one)
- 3.1 Using a Foreign Key
  - user 테이블과 address 테이블이 있다. user 테이블에 외부키로 address_id 컬럼이 있다.
- 3.2 Using a Shared Primary Key
- 3.3 Using a Join Table

---
## 참고사항
- createQuery()와 createSQLQuery()의 차이( 2022.01.11 )
    - createQuery()는 HSQL을 사용한다. persistence context에 있는 엔티티와 동기화된 결과를 반환한다.
    - 반면, createSQLQuery()는 데이터베이스에 있는 rows를 대상으로 조회한다.
    - 주의할 점은 createQuery에 의해 동기화된 이후에는 native를 이용한 쿼리도 동기화된 결과를 반환한다.
    - 예를 들면, 초기 데이터베이스에는 3개의 행이 있다. transient entity를 session.save(entity)한다. entity는 persistent 상태가 된다. 이때 바로 native 쿼리로 count하면 새 엔티티가 반영되지 않은 결과를 반환한다(3개 행). 그리고 HQL로 count쿼리를 실행하면 4를 반환한다. 이후에 다시 native를 실행하면 3이 아닌 4를 반환한다.
    - 위 예시에서 두 번의 동일한 native 쿼리가 서로 다른 값을 반환한다. 중간에 실행되는 HQL쿼리가 영향을 끼친것으로 보인다. 이는 **unrepeatable read**문제와 유사하다.
    - HB의 상태와 별개로 데이터베이스의 상태만 알고싶다면 jdbc를 직접 사용하는게 안전하다.
