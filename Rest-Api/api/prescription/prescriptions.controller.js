const { addPrescription, getPrescription } = require("./prescriptions.service");

module.exports = {
    addPrescription: (req, res) => {

        data = req.body
        addPrescription(data, (err, results) => {
            if (err) {
                console.log(err);
                return res.status(500)
            }
            return res.status(200).json({ code: 200, serverMsg: "success" })

        })


    },

    getPrescription:(req,res) =>{
        getPrescription(req.params.appointment_id,(err,results)=>{
            if(err){
                return res.status(500).json({
                    serverMsg:err.message
                })
            }
        
        
            return res.status(200).json(results)
        })

    }
}
