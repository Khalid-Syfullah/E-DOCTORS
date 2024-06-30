const {register,login,loginWithToken,findByPhone, updatePassword,updatePicture} =require("./user.controller")

const router= require('express').Router()


router.post("/register",register)
router.post("/login",login)
router.post("/validateToken",loginWithToken)
router.post("/findByPhone",findByPhone)
router.patch("/updatePassword",updatePassword)
router.patch('/updatePicture',updatePicture)


module.exports = router