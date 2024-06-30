const {createPool} = require('mysql');

const config = {
    port:process.env.DB_PORT,
    host:process.env.DB_HOST,
    user:process.env.DB_USER,
    password:process.env.DB_PASS,
    database:process.env.DB_NAME,
    connectionLimit:10
   
}
const pool = createPool(config)

module.exports = pool