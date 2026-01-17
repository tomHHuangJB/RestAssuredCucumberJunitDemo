module.exports = (req, res, next) => {
  // Validate customer creation payloads to support negative tests.
  if (req.method === "POST" && req.path === "/customers") {
    const name = req.body && req.body.name;
    if (typeof name !== "string" || name.trim() === "") {
      return res.status(400).json({ error: "name is required" });
    }
  }

  return next();
};
