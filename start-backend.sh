docker run -d \
  --name api-backend \
  -p 3100:80 \
  -v "$(pwd)/db/db.json:/data/db.json" \
  -v "$(pwd)/db/middleware.js:/middleware.js" \
  clue/json-server \
  --watch /data/db.json \
  --middlewares /middleware.js
