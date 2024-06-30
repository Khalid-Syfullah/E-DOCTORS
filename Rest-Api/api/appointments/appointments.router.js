const router = require("express").Router();
const {
  getAppointment,
  getDoctorAppointmentList,
  getPatientAppointmentList,
  updateCurrentSerial,
  getCurrentSerial,
  addReview,
} = require("./appointments.controller");

router.post("/getAppointment", getAppointment);
router.post("/getDoctorAppointmentList/:doctor_id", getDoctorAppointmentList);
router.post(
  "/getPatientAppointmentList/:patient_id",
  getPatientAppointmentList
);
router.post("/updateCurrentSerial", updateCurrentSerial);
router.get("/getCurrentSerial/:doctor_id", getCurrentSerial);
router.post("/addReview", addReview);
module.exports = router;
