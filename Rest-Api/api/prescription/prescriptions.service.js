const pool = require("../../config/db_conn");

const syncSql = require("sync-sql")


const config = {
    port: process.env.DB_PORT,
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,

}

module.exports = {

    addPrescription: (data, callback) => {


        pool.query(`Insert into prescriptions(appointment_id,medicines,date) values(?,?,?) `,
            [
                data.appointment_id,
                data.medicines,
                data.date
                
            ]
            , (error, results) => {
                if (error) {
                    return callback(error);
                }
                return callback(null, results);
            });
    },
    getPrescription: (data,callback) =>{
        pool.query('SELECT * FROM `prescriptions` WHERE `appointment_id` =?',[data],(err,results)=>{
            if(err){
               
                return callback(err)
            }
            return callback(null,results[0])
        })

    }

}
