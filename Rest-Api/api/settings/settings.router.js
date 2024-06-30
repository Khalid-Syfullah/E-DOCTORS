const { setStatus, unSetStatus } = require("./settings.controller");

const router = require("express").Router();

router.post("/setStatus", setStatus);
router.post("/unSetStatus", unSetStatus);

module.exports = router;
