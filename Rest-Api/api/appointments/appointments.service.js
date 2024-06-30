const pool = require("../../config/db_conn");

const syncSql = require("sync-sql");

const config = {
  port: process.env.DB_PORT,
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
};

let today = new Date().toISOString().slice(0, 10);

module.exports = {
  getAppointment: (data, callback) => {
    pool.query(
      `select max(serial) as max from appointments where doctor_id=? and date = ?`,
      [data.doctor_id, data.date],
      (err, serialResult) => {
        if (err) {
          return callback(err);
        }
        var serial = JSON.parse(JSON.stringify(serialResult))[0].max;
        var link =
          data.patient_id.toString() +
          data.doctor_id.toString() +
          Date.now().toString();
        console.log(serial);
        if (serial == null) {
          serial = 0;
        }
        pool.query(
          `Insert into appointments(doctor_id,patient_id,link,payment_status,date,serial) values(?,?,?,?,?,?) `,
          [data.doctor_id, data.patient_id, link, 0, today, serial + 1],
          (error, results) => {
            if (error) {
              return callback(error);
            }
            return callback(null, results);
          }
        );
      }
    );
  },
  getAppointmentOffline: (data, callback) => {
    console.log(data);
    pool.query(
      `select max(serial) as max from appointments_offline where doctor_id=? and date = ?`,
      [data.doctor_id, data.date],
      (err, serialResult) => {
        if (err) {
          return callback(err);
        }
        var serial = JSON.parse(JSON.stringify(serialResult))[0].max;
        console.log(serial);
        if (serial == null) {
          serial = 0;
        }
        pool.query(
          `Insert into appointments_offline(doctor_id,patient_id,date,serial) values(?,?,?,?) `,
          [data.doctor_id, data.patient_id, data.date, serial + 1],
          (error, results) => {
            if (error) {
              return callback(error);
            }
            return callback(null, results);
          }
        );
      }
    );
  },
  getPatientAppointmentList: (patient_id, callback) => {
    console.log(patient_id, today);
    pool.query(
      `select * from appointments where patient_id = ? and date = ?`,
      [patient_id, today],
      (err, results) => {
        if (err) {
          return callback(err);
        }

        resultsJson = JSON.parse(JSON.stringify(results));

        for (let i = 0; i < resultsJson.length; i++) {
          const query = `select * from doctors where bmdc = ${resultsJson[i].doctor_id}`;
          var output = syncSql.mysql(config, query).data.rows;
          output[0].password = "";
          resultsJson[i].doctorData = output[0];

          console.log(resultsJson);
        }

        return callback(null, resultsJson);
      }
    );
  },
  getDoctorAppointmentList: (doctor_id, callback) => {
    pool.query(
      `select * from appointments where doctor_id = ?`,
      [doctor_id],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        resultsJson = JSON.parse(JSON.stringify(results));

        for (let i = 0; i < resultsJson.length; i++) {
          const query = `select * from users where phone = ${resultsJson[i].patient_id}`;
          var output = syncSql.mysql(config, query).data.rows;
          output[0].password = "";
          resultsJson[i].patientData = output[0];

          console.log(resultsJson);
        }

        return callback(null, resultsJson);
      }
    );
  },
  updateCurrentSerial: (data, callback) => {
    pool.query(
      `UPDATE current_serial SET serial = ? WHERE doctor_id = ?`,
      [data.serial, data.doctor_id],
      (error, results) => {
        if (error) {
          console.log(error);
          return callback(error);
        }
        return callback(null, results);
      }
    );
  },
  getCurrentSerial: (doctor_id, callback) => {
    pool.query(
      `select serial from current_serial where doctor_id = ?`,
      [doctor_id],
      (error, results) => {
        if (error) {
          return callback(error);
        }
        return callback(null, results);
      }
    );
  },
  addReview: (data, callback) => {
    pool.query(
      `UPDATE appointments SET rating=?,review=?,review_date=? WHERE link=?`,
      [data.rating, data.review, today, data.link],
      (error, results) => {
        if (error) {
          console.log(error);
          return callback(error);
        }
        return callback(null, results);
      }
    );
  },
};
