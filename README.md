[![CI](https://github.com/tomHHuangJB/RestAssuredCucumberJunitDemo/actions/workflows/ci.yml/badge.svg)](
https://github.com/tomHHuangJB/RestAssuredCucumberJunitDemo/actions/workflows/ci.yml
)

# API Test Automation Demo (JUnit + Cucumber)

This repository demonstrates a clean, interview-ready API test automation setup using:

- **JUnit 4 + Rest Assured** for engineering-focused API tests
- **Cucumber + Rest Assured** for behavior-driven (BDD) scenarios
- **Dockerized JSON Server backend** for deterministic and reproducible testing

---

## Project Structure

apiTest2/
├── docker/
│   ├── db.json
│   └── middleware.js
│
├── api-tests-junit/
│   └── src/test
│
└── README.md

---

## Backend (Docker)

Start the API backend:

docker rm -f api-backend 2>/dev/null || true
docker run -d \
--name api-backend \
-p 3100:80 \
-v "$(pwd)/db/db.json:/data/db.json" \
-v "$(pwd)/middleware.js:/middleware.js" \
clue/json-server \
--watch /data/db.json \
--middlewares /middleware.js

or If you want the validation middleware from db/middleware.js, use this variant:

docker rm -f api-backend 2>/dev/null || true
docker run -d \
--name api-backend \
-p 3100:80 \
-v "$(pwd)/db/db.json:/data/db.json" \
-v "$(pwd)/db/middleware.js:/middleware.js" \
clue/json-server \
--watch /data/db.json \
--middlewares /middleware.js

check log for docker issue:
docker logs api-backend


Base URL:

http://localhost:3100

---

## Running Tests

cd api-tests-junit
./gradlew test

---

## Notes

- Build artifacts and documentation are intentionally excluded from version control
- The same backend is reused for both JUnit and Cucumber tests
- Designed to run locally and in CI without modification
```

---

## 4. Git Commit Command

Once everything looks correct:

```
git add .
git commit -m "Initial API test automation demo with JUnit, Cucumber, and Docker backend"
```

---
# Docker Setup for API Backend (JSON Server)

Step by step, how to start the API backend
using Docker. It is written for readers who first `cd docker`
before running commands.

----------------------------------------------------------------
PREREQUISITES
----------------------------------------------------------------

- Docker Desktop installed and running
- Repository cloned locally
- You are at the repository root

----------------------------------------------------------------
STEP 1: GO TO THE DOCKER DIRECTORY
----------------------------------------------------------------

From the repository root:

cd docker

Verify required files exist:

ls

You should see:
- db.json
- middleware.js   (if validation is enabled)

----------------------------------------------------------------
STEP 2: STOP ANY RUNNING CONTAINERS ON PORT 3100
----------------------------------------------------------------

This prevents port and container name conflicts.

docker ps --filter "publish=3100" -q | xargs -r docker rm -f
docker rm -f json-server 2>/dev/null || true

Explanation:
- Removes any container currently bound to port 3100
- Removes an existing container named "json-server" if present
- Safe to run multiple times (idempotent)

----------------------------------------------------------------
STEP 3: START THE API CONTAINER (FOREGROUND MODE)
----------------------------------------------------------------

Run the following command from inside the docker/ directory:

docker run \
  --name json-server \
  -p 3100:80 \
  -v $(pwd)/db.json:/data/db.json \
  -v $(pwd)/middleware.js:/middleware.js \
  clue/json-server \
  --middlewares /middleware.js

Expected output includes:
- Loading db.json
- Loading /middleware.js
- Resources http://localhost:80/users

The API is now running.

IMPORTANT:
- This terminal is now occupied by Docker
- Leave it open while the API is running

----------------------------------------------------------------
STEP 4: VERIFY THE API (NEW TERMINAL TAB)
----------------------------------------------------------------

Open a new terminal tab and run:

curl http://localhost:3100/users

Expected:
- HTTP 200
- JSON response from db.json

----------------------------------------------------------------
STEP 5: VERIFY VALIDATION MIDDLEWARE (IF ENABLED)
----------------------------------------------------------------

Missing required field (should return HTTP 400):

curl -i -X POST http://localhost:3100/customers \
  -H "Content-Type: application/json" \
  -d '{ "email": "no-name@example.com" }'

Valid payload (should return HTTP 201):

curl -i -X POST http://localhost:3100/customers \
  -H "Content-Type: application/json" \
  -d '{ "name": "Valid User", "email": "valid@example.com" }'

----------------------------------------------------------------
STEP 6: OPTIONAL — RUN DOCKER IN BACKGROUND (DETACHED MODE)
----------------------------------------------------------------

If you do not want Docker to occupy your terminal:

docker ps --filter "publish=3100" -q | xargs -r docker rm -f
docker rm -f json-server 2>/dev/null || true

docker run -d \
  --name json-server \
  -p 3100:80 \
  -v $(pwd)/db.json:/data/db.json \
  -v $(pwd)/middleware.js:/middleware.js \
  clue/json-server \
  --middlewares /middleware.js

View logs:

docker logs -f json-server

Stop the container:

docker rm -f json-server

----------------------------------------------------------------
STEP 7: RUN API TESTS
----------------------------------------------------------------

From the repository root:

cd api-tests-junit
./gradlew test

This runs:
- JUnit + Rest Assured API tests
- Cucumber scenarios via JUnit runner

Final desired structure:

src/test/java
├── api
│   ├── customers
│   │   └── CreateCustomerTest.java
│   └── users
│       └── LoginTest.java
│
└── bdd
    ├── runner
    │   └── CucumberTest.java
    └── steps
        └── CustomerSteps.java


## END OF FILE
