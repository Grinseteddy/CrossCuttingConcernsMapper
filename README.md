Run
---

    mvn spring-boot:run
    
Test
---

Open http://localhost:8082/mapping?inputString=Abc

Build docker image 
---

	docker build -t mapper:latest .

Run with docker
---

	docker run -p 8082:8082 mapper:latest