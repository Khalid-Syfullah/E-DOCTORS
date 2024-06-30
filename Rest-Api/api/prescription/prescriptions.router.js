const {addPrescription, getPrescription} = require('./prescriptions.controller')

const router= require('express').Router()


router.post("/add",addPrescription)
router.get("/getPrescription/:appointment_id",getPrescription)

module.exports = router