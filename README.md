# Ticketing System – Microservices Application

## Overview
Ova aplikacija predstavlja sistem za kupovinu karata za događaje (koncerte, predstave, sportske utakmice).  
Cilj sistema je da omogući:
- Korisnicima – kupovinu i otkazivanje karata, kao i pregled svih svojih aktivnih karata.  
- Administratorima – kreiranje novih događaja, definisanje kapaciteta i ograničenja po kupcu.  
- Javnom API-ju – pregled svih dostupnih događaja bez autentikacije.  

Aplikacija je implementirana kroz tri mikroservisa, razvijena u skladu sa principima heksagonalne arhitekture i povezana sa PostgreSQL bazom i Kafka infrastrukturom.  

---

## Microservices Architecture

### 1. Ticket Service (port `8081`)
- Funkcionalnosti:
  - Kupovina karata (`/tickets/purchase`)  
  - Otkazivanje karata (`/tickets/cancel`)  
  - Pregled svih kupljenih i aktivnih karata (`/tickets`)  
- Komunikacija:
  - REST API – svi endpoint-i zahtevaju JWT token u header-u.  
  - Kafka listener – podržava kupovinu preko Kafka poruka. Token i ostali podaci šalju se u body-u poruke.  
- Persistencija: PostgreSQL (output adapter – JPA).  

### 2. Event Service (port `8082`)
- Funkcionalnosti:
  - Interni API – komunikacija sa Ticket servisom prilikom kupovine/otkazivanja (provera dostupnih karata).  
  - Public API (`/events/public`) – vraća listu aktivnih događaja, dostupan bez autentikacije.  
  - Admin API (`/events/admin`) – kreiranje novih događaja (samo za korisnike sa admin privilegijama).  
- Persistencija: PostgreSQL (output adapter – JPA).  

### 3. User Service (port `8083`)
- Funkcionalnosti:
  - Registracija (`/users/register`)  
  - Login (`/users/login`) → vraća JWT token  
  - `/me` ruta – vraća podatke o trenutno ulogovanom korisniku (koristi se za internu komunikaciju).  
- Proxy prema eksternom servisu: [DummyJSON Users API](https://dummyjson.com/docs/users).  

---

## Security
- JWT autentikacija i autorizacija:
  - Svi korisnički i administratorski API endpoint-i zahtevaju validan JWT token.  
  - Samo public API za događaje je otvoren i ne zahteva autentikaciju.  
- Administratorske rute – dostupne isključivo korisnicima sa admin privilegijama.  

---

## Infrastructure
Aplikacija koristi sledeće servise:  
- PostgreSQL – baza podataka za Ticket i Event servise.  
- Kafka – sistem za asinkronu obradu kupovine karata.  
- AKHQ – UI za pregled i testiranje Kafka topica (dostupan na portu `8086`).  

Sve komponente se podižu korišćenjem Docker Compose.  

---

## How to Run

1. Klonirati repozitorijum:  
   ```bash
   git clone <repo-url>
   cd <repo-folder>
   ```

2. Pokrenuti sve servise:  
   ```bash
   docker-compose up --build
   ```

3. Nakon podizanja, dostupni su sledeći servisi:  

| Service        | Port   | Swagger UI / UI path                     |
|----------------|--------|------------------------------------------|
| Ticket Service | 8081   | `http://localhost:8081/swagger-ui/index.html` |
| Event Service  | 8082   | `http://localhost:8082/swagger-ui/index.html` |
| User Service   | 8083   | `http://localhost:8083/swagger-ui/index.html` |
| AKHQ (Kafka UI)| 8086   | `http://localhost:8086`                       |

---

## API Testing

### 1. Login i dobijanje JWT tokena
- Otići na **User Service** Swagger (`http://localhost:8083/swagger-ui/index.html`).  
- Registrujte ili se ulogujte korisničkim nalogom.  
- Dobijeni token koristiti u ostalim servisima (dodati ga u **Authorization header** kao `Bearer <token>`).  

### 2. Kreiranje događaja (admin API)
- Putem **Event Service** → `/events/admin`.  
- Potrebno je koristiti token korisnika sa admin privilegijama.  

### 3. Pregled dostupnih događaja
- Putem **Event Service** → `/events/public`.  
- Dostupno bez autentikacije.  

### 4. Kupovina karata
- Putem **Ticket Service** → `/tickets/purchase`.  
- Neophodan validan JWT token.  

### 5. Otkazivanje karata
- Putem **Ticket Service** → `/tickets/cancel`.  
- Otkazane karte se vraćaju u slobodan inventar.  

### 6. Kupovina preko Kafka UI (AKHQ)
- Otići na `http://localhost:8086`.  
- Na odgovarajući topic poslati JSON zahtev u formatu:  
  ```json
  {
    "eventId": 1,
    "quantity": 2,
    "userToken": "<jwt-token>"
  }
  ```

---

## Documentation and Tools
- Svaki servis poseduje integrisan **Swagger UI** za interaktivno testiranje.  
- Dostupna je i **Postman kolekcija** u repozitorijumu sa svim zahtevima i primerima.  

---

## Technology Stack
- Java 21  
- Spring Boot (REST, Security, Data JPA)  
- PostgreSQL  
- Kafka  
- Docker & Docker Compose  
- AKHQ (Kafka UI)  

---

## Possible Improvements
- Implementacija distributed tracing-a (npr. OpenTelemetry).  
- Dodavanje rate limiting-a za zaštitu od zloupotrebe.  
- CI/CD pipeline za automatsko pokretanje build-a i testova.  
