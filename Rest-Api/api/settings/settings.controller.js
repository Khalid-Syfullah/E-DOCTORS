const { setStatus, unSetStatus } = require("./settings.service");

module.exports = {
  setStatus: (req, res) => {
    console.log(req.body);
    setStatus(req.body, (err) => {
      if (err) {
        console.log(err.message);
        return res.status(500).json({ serverMsg: err.message });
      }

      return res.status(200).json({ serverMsg: "success" });
    });
  },
  unSetStatus: (req, res) => {
    unSetStatus(req.body, (err) => {
      if (err) {
        console.log(err.message);
        return res.status(500).json({ serverMsg: err.message });
      }

      return res.status(200).json({ serverMsg: "success" });
    });
  },
};
