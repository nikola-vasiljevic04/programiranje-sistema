# Eco-Travel Avanture Service

Ovaj mikroservis je odgovoran za upravljanje katalogom avantura i vrši orkestraciju podataka između korisničkih zahteva i eksternih servisa, obezbeđujući verifikaciju vodiča i generisanje izveštaja.

## Technical Specifications
- **Framework:** Spring Boot 3.2.x
- **Language:** Java 21
- **Communication:** Spring Cloud OpenFeign (Inter-service communication)
- **Patterns:** Facade Pattern, Factory Method Pattern
- **Database:** H2 (In-Memory)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8081

## Business Logic & Features
1. **Microservices Orchestration (Facade):** Putem `AdventureFacade` objekta, servis transparentno objedinjuje podatke o avanturama iz lokalne baze sa profilima vodiča koji dolaze sa porta **8082**. Korisnik dobija jedinstven odgovor bez poznavanja kompleksnosti u pozadini.
2. **Dynamic Report Generation (Factory):** Implementiran `ReportFactory` koji na osnovu "Completeness" skora vodiča (dobijenog sa 8082) odlučuje koji tip izveštaja će generisati.
    - **Eco-Report:** Za vodiče sa visokim stepenom stručnosti i popunjenim profilom (iznad definisanog praga).
    - **Standard-Report:** Za profile koji ispunjavaju samo osnovne kriterijume.
3. **Guide Verification Logic:** Servis vrši automatsku proveru podobnosti vodiča za specifičnu turu upoređivanjem zahtevanih jezika i sertifikata avanture sa stvarnim kvalifikacijama vodiča u realnom vremenu preko mreže.
4. **Resilient Integration:** Koristi Feign klijente sa definisanim DTO strukturama koje osiguravaju da se podaci sa `vodici-service` ispravno mapiraju i validiraju pre dalje obrade u poslovnoj logici.

## API Endpoints

### Adventure Management
| Method | Endpoint | Description |
|:---|:---|:---|
| `GET` | `/api/adventures` | Lista svih avantura |
| `GET` | `/api/adventures/{id}` | **Facade** prikaz: podaci o avanturi + podaci o vodiču |
| `GET` | `/api/adventures/{id}/verify-guide` | Provera kvalifikacija vodiča za tu avanturu |
| `GET` | `/api/adventures/{id}/report` | **Factory** generisanje izveštaja (Eco/Standard) |

### System Monitoring
| Method | Endpoint | Description |
|:---|:---|:---|
| `GET` | `/actuator/health` | Provera statusa servisa i povezanosti sa bazom |
| `GET` | `/actuator/metrics` | Uvid u performanse i broj HTTP zahteva |

## Local Setup & Testing
1. **Pre-requisite:** Pokrenut `vodici-service` na portu **8082**.
2. **Build:** `mvn clean install`
3. **Run:** `mvn spring-boot:run`
4. **Swagger UI:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Test Scenario (Factory & Facade)
1. **Integracija:** Pozvati `GET /api/adventures/1`. Proveriti da li je `guide` objekat popunjen podacima sa porta 8082 (Milan Stojičević