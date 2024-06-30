const {
  register,
  findByPhone,
  updatePassword,
  updatePicture,
} = require("./user.service");
const { genSaltSync, hashSync, compareSync } = require("bcryptjs");
const { sign, verify } = require("jsonwebtoken");
const fs = require("fs");
module.exports = {
  register: (req, res) => {
    const body = req.body;
    const phone = body.phone;
    findByPhone(phone, (err, results) => {
      if (err) {
        console.log(err);
        return res.status(500).json({
          serverMsg: "fatal error",
        });
      }
      if (results.length > 0) {
        return res.json({
          serverMsg: "User already exists",
        });
      }
      const salt = genSaltSync(10);
      body.password = hashSync(body.password, salt);

      register(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            serverMsg: "database connection error",
          });
        }

        return res.status(200).json({
          serverMsg: "success",
        });
      });
    });
  },
  login: (req, res) => {
    const body = req.body;
    if (body.phone == "" || body.password == "") {
      return res.json({
        serverMsg: "invalid credentials",
      });
    }
    findByPhone(body.phone, (err, results) => {
      if (err) {
        return res.json({ serverMsg: err.message });
      }
      if (!results) {
        return res.json({
          serverMsg: "invalid credentials",
        });
      }
      userData = results[0];
      const result = compareSync(body.password, userData.password);
      if (result) {
        userData.password = "";
        const jsontoken = sign(
          { result: userData },
          process.env.AUTH_TOKEN_SECRET,
          {
            expiresIn: "7d",
          }
        );
        userData.token = jsontoken;
        userData.serverMsg = "success";
        return res.json(userData);
      } else {
        return res.json({
          serverMsg: "invalid credentials",
        });
      }
    });
  },
  loginWithToken: (req, res) => {
    try {
      const verified = verify(req.body.token, process.env.AUTH_TOKEN_SECRET);
      req.user = verified;
      verified.result.serverMsg = "success";
      res.json(verified.result);
    } catch (err) {
      return res.json({ serverMsg: "login required" });
    }
  },
  updatePassword: (req, res) => {
    const body = req.body;
    const phone = body.phone;
    findByPhone(phone, (err, results) => {
      if (err) {
        console.log(err);
        return res.status(500).json({
          serverMsg: "fatal error",
        });
      }
      if ((results.length = 0)) {
        return res.json({
          serverMsg: "User does not exist",
        });
      }
      const salt = genSaltSync(10);
      body.password = hashSync(body.password, salt);

      updatePassword(body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            serverMsg: "database connection error",
          });
        }

        return res.json({
          serverMsg: "password updated",
        });
      });
    });
  },
  findByPhone: (req, res) => {
    console.log(req.body);
    const body = req.body;
    findByPhone(body.phone, (err, results) => {
      if (err) {
        return res.status(500);
      }
      if (!results) {
        return res.json({
          serverMsg: "invalid credentials",
        });
      }
      if (results.length > 0) {
        results[0].serverMsg = "user already exists";
        return res.json(results[0]);
      } else {
        return res.json({
          serverMsg: "user not found",
        });
      }
    });
  },
  updatePicture: (req, res) => {
    var image = req.body.image;
    var image_path = "images/users/" + req.body.phone + ".jpeg";
    fs.writeFile(image_path, image, { encoding: "base64" }, (err) => {
      if (err) {
        console.log(err);
        return res.send("failed");
      }

      console.log("success");
      req.body.image_path = image_path;
      updatePicture(req.body, (err, results) => {
        if (err) {
          console.log(err);
          return res.status(500).json({
            serverMsg: "database connection error",
          });
        }

        return res.json({
          serverMsg: "picture updated",
        });
      });
    });
  },
};
