const pool = require("../../config/db_conn");

const syncSql = require("sync-sql");

const config = {
  port: process.env.DB_PORT,
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
};

module.exports = {
  setStatus: (data, callback) => {
    pool.query(
      `UPDATE doctors SET status = ? WHERE bmdc = ?`,
      [true, data.bmdc],
      (error, results) => {
        if (error) {
          console.log(error);
          return callback(error);
        }
        return callback(null, results);
      }
    );
  },
  unSetStatus: (data, callback) => {
    pool.query(
      `UPDATE doctors SET status = ? WHERE bmdc = ?`,
      [false, data.bmdc],
      (error, results) => {
        if (error) {
          console.log(error);
          return callback(error);
        }
        console.log(results);
        return callback(null, results);
      }
    );
  },
};
