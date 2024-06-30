const {
  getAppointment,
  getDoctorAppointmentList,
  getPatientAppointmentList,
  updateCurrentSerial,
  getCurrentSerial,
  addReview,
  getAppointmentOffline,
} = require("./appointments.service");

module.exports = {
  getAppointment: (req, res) => {
    if (req.body.type == "online") {
      getAppointment(req.body, (err) => {
        if (err) {
          return res.json({ serverMsg: err.message });
        }

        return res.status(200).json({ serverMsg: "success" });
      });
    } else {
      getAppointmentOffline(req.body, (err) => {
        if (err) {
          return res.json({ serverMsg: err.message });
        }

        return res.status(200).json({ serverMsg: "success" });
      });
    }
  },
  getDoctorAppointmentList: (req, res) => {
    getDoctorAppointmentList(req.params.doctor_id, (err, results) => {
      if (err) {
        return res.status(500).json({
          serverMsg: err.message,
        });
      }
      console.log(results);

      return res.status(200).json(results);
    });
  },
  getPatientAppointmentList: (req, res) => {
    getPatientAppointmentList(req.params.patient_id, (err, results) => {
      if (err) {
        return res.status(500).json({
          serverMsg: err.message,
        });
      }
      return res.status(200).json(results);
    });
  },
  updateCurrentSerial: (req, res) => {
    updateCurrentSerial(req.body, (err) => {
      if (err) {
        console.log(err.message);
        return res.status(500).json({ serverMsg: err.message });
      }

      return res.status(200).json({ serverMsg: "success" });
    });
  },
  getCurrentSerial: (req, res) => {
    getCurrentSerial(req.params.doctor_id, (err, results) => {
      if (err) {
        return res.status(500).json({
          serverMsg: err.message,
        });
      }

      return res.status(200).json(results[0]);
    });
  },
  addReview: (req, res) => {
    console.log(req.body);
    addReview(req.body, (err, result) => {
      if (err) {
        return res.status(500);
      }
      return res.json({ serverMsg: "success" });
    });
  },
};
