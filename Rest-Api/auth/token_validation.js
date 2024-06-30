const {verify} =require("jsonwebtoken")

module.exports = {
    checkToken: (req,res,next) => {
        let token= req.header("authorization")
        if(token){
           
            verify(token, process.env.AUTH_TOKEN_SECRET,(err,decoded)=>{
                if(err){
                    res.json({
                        success:0,
                        message: "invalid token"
                    })
                }
                else{
                    next()
                }
            })

        }else{
            res.json({
                success:0,
                message:"access denied"
            })
        }
    }
}