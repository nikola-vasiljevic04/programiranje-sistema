# Eco-Travel Vodici Service

Ovaj mikroservis upravlja profilima planinarskih vodiča, njihovim kvalifikacijama i jezicima, čineći srž operativnog dela Eco-Travel sistema.

## Technical Specifications
- **Framework:** Spring Boot 3.2.x
- **Language:** Java 21
- **Database:** H2 (In-Memory)
- **Mappers:** MapStruct (Entity-DTO conversion)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8082

## Business Logic & Features
1. **Dynamic Completeness Calculation:** Sistem automatski računa procenat popunjenosti profila na osnovu unete biografije, sertifikata i jezika. Ovo omogućava filtriranje samo potpuno kvalifikovanih vodiča.
2. **Certification & Qualification Tracking:** Omogućava dodavanje stručnih sertifikata vodičima, što direktno utiče na njihov kredibilitet i "completeness" skor.
3. **Multi-language Support:** Detaljna evidencija jezika koje vodič govori, što je ključni preduslov za verifikaciju vodiča za specifične internacionalne avanture.
4. **Data Consistency:** Koristi MapStruct sa specifičnim bindingom za Lombok kako bi se osigurala konzistentnost podataka između baze i eksternih API odgovora.

## API Endpoints

### Guide Profiles
| Method | Endpoint | Description |
|:---|:---|:---|
| `GET` | `/api/guide-profiles` | Lista svih profila |
| `GET` | `/api/guide-profiles/{id}` | Detaljni podaci o vodiču |
| `GET` | `/api/guide-profiles/{id}/completeness` | Vraća procenat popunjenosti profila |
| `POST` | `/api/guide-profiles` | Kreiranje novog profila (inicijalizuje skor) |
| `PUT` | `/api/guide-profiles/{id}` | Ažuriranje podataka i rekalkulacija skora |
| `DELETE` | `/api/guide-profiles/{id}` | Uklanjanje profila |

### Languages & Certifications
| Method | Endpoint | Description |
|:---|:---|:---|
| `POST` | `/api/languages` | Dodavanje novog jezika u katalog |
| `POST` | `/api/certifications` | Registracija sertifikata za vodiča |

## Local Setup & Testing
1. **Build:** `mvn clean install`
2. **Run:** `mvn spring-boot:run`
3. **Swagger:** [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)