# Spring Boot with Hibernate [D002]
- This project is a practice field for Hibernate with Spring Boot
- Basically, referring to [Baeldung's guides](https://www.baeldung.com/)

- Web, JPA, H2를 포함해서 스프링 부트 프로젝트 생성.

---

## [1. JPA Entity Lifecycle Events](https://www.baeldung.com/jpa-entity-lifecycle-events)

- 1.1 introdunction
    - entity의 lifecycle이 진행되는 동안 발생하는 이벤트가 몇가지 있다. 어떤 종류의 이벤트가 있는지, 어노테이션을 사용해서 이벤트 콜백을 어떻게 다루는지 살펴본다.

- 1.2 JPA Entity Lifecycle Events
    - @PrePersist
    - @PostPersist
    - @PreRemove
    - @PostRemove
    - @PreUpdate
    - @PostUpdate
    - @PostLoad
    - 두 가지 방법   
        1. Annotating methods in the entity.
        2. EntityListener with annotated callback methods.

---

### - H2의 유용한 기능
- lightweight databast.
-  콘솔 사용가능.ㄴ Write in application.properties.
    >  spring.h2.console.enabled=true
- http://localhost:8080/h2-console/ 에서 확인할 수 있다.

