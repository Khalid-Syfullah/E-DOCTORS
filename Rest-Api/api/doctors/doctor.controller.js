const { compareSync } = require("bcryptjs");
const {
  getAllDoctors,
  loginDoctor,
  findDoctorById,
  findDoctorByPhone,
  register,
  resetPassword,
  updatePicture,
  getAllReviews,
} = require("./doctor.service");
const { sign, verify } = require("jsonwebtoken");
const fs = require("fs");

module.exports = {
  getAllDoctors: (req, res) => {
    getAllDoctors((err, results) => {
      if (err) {
        return res.json({ serverMsg: err.message });
      }
      if (!results) {
        return res.json({
          serverMsg: "invalid credentials",
        });
      }
      if (results.length == 0) {
        return res.json({ serverMsg: "no doctor exists" });
      } else {
        return res.json(results);
      }
    });
  },
  loginDoctor: (req, res) => {
    console.log(req.body);
    loginDoctor(req.body, (err, results) => {
      if (err) {
        console.log(err);
        return res.status(500);
      }
      console.log(results[0]);
      if (!results) {
        return res.status(200).json({
          serverMsg: "invalid credentials",
        });
      }
      if (results.length == 0) {
        return res.status(200).json({ serverMsg: "invalid credentials" });
      } else {
        if (compareSync(req.body.password, results[0].password)) {
          results[0].password = "";
          const jsontoken = sign(
            { result: results[0] },
            process.env.AUTH_TOKEN_SECRET,
            {
              expiresIn: "7d",
            }
          );
          results[0].token = jsontoken;
          results[0].serverMsg = "success";

          return res.status(202).json(results[0]);
        } else {
          return res.status(200).json({ serverMsg: "Invalid credentials" });
        }
      }
    });
  },
  loginWithToken: (req, res) => {
    try {
      const verified = verify(req.params.token, process.env.AUTH_TOKEN_SECRET);
      req.user = verified;
      verified.result.serverMsg = "success";
      res.status(202).json(verified.result);
    } catch (err) {
      return res.status(200).json({ serverMsg: "login required" });
    }
  },
  findDoctorById: (req, res) => {
    console.log(req.params.bmdc);
    findDoctorById(req.params.bmdc, (err, results) => {
      if (err) {
        return res.json({ serverMsg: err.message });
      }
      if (!results) {
        return res.status(500).json({
          serverMsg: "invalid credentials",
        });
      }
      if (results.length > 0) {
        results[0].serverMsg = "Doctor found";
        return res.json(results[0]);
      } else {
        return res.json({
          serverMsg: "user not found",
        });
      }
    });
  },
  findDoctorByPhone: (req, res) => {
    findDoctorByPhone(req.params, (err, results) => {
      if (err) {
        return res.json({ serverMsg: err.message });
      }
      if (results.length > 0) {
        return res.status(200).json({
          serverMsg: "Phone Number Already exists",
          code: 0,
        });
      } else {
        return res.status(200).json({
          serverMsg: "Doesn't exist",
          code: 1,
        });
      }
    });
  },
  register: (req, res) => {
    console.log(req.body);
    findDoctorById(req.body.doctor_id, (err, results) => {
      if (err) {
        return res.status(500).json({ serverMsg: err.message });
      }
      if (results.length > 0) {
        return res.status(200).json({
          serverMsg: "Doctor With this BMDC already exists",
          code: -1,
        });
      }

      register(req.body, (err, results) => {
        if (err) {
          console.log(err);
          return res
            .status(500)
            .json({ serverMsg: "Something went wrong", code: 0 });
        }
        return res
          .status(200)
          .json({ serverMsg: "Registration Successfull", code: 1 });
      });
    });
  },

  resetPassword: (req, res) => {
    resetPassword(req.body, (err, results) => {
      if (err) {
        console.log(err);
        return res.status(500);
      }
      return res.status(200).json({ serverMsg: "Success", code: 1 });
    });
  },

  updatePicture: (req, res) => {
    var image = req.body.image;
    var image_path = "images/doctors/" + req.body.phone + ".jpeg";
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
  getAllReviews: (req, res) => {
    getAllReviews(req.params.doctor_id, (err, results) => {
      if (err) return res.status(500);
      return res.status(200).json(results);
    });
  },
};
