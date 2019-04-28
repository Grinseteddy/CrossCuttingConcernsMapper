Run
---

    mvn spring-boot:run
    
Test
---

Open http://localhost:8082/mapping/Abc

Build docker image 
---

	docker build -t mapper:latest .

Run with docker
---

	docker run --network=host -p 8082:8082 mapper:latest