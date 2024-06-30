const pool = require("../../config/db_conn");

module.exports = {
  register: (data, callback) => {
    pool.query(
      `insert into users (phone,first_name,last_name,email,age,gender,password) values(?,?,?,?,?,?,?)`,
      [
        data.phone,
        data.first_name,
        data.last_name,
        data.email,
        data.age,
        data.gender,
        data.password,
      ],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        return callback(null, results);
      }
    );
  },
  findByPhone: (phone, callback) => {
    pool.query(`select * from users where phone=?`, [phone], (err, results) => {
      if (err) {
        return callback(err);
      }
      return callback(null, results);
    });
  },
  updatePassword: (data, callback) => {
    pool.query(
      `update users set password=? where phone=?`,
      [data.password, data.phone],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        return callback(null, results);
      }
    );
  },
  updatePicture:(data,callback)=>{
    pool.query(
    `update users set image_url=? where phone=?`,
      [data.image_path, data.phone],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        return callback(null, results);
      }
    );
  }
};
