const pool = require("../../config/db_conn");

module.exports = {
  uploadBlog: (data, callback) => {
    pool.query(
      "INSERT INTO `blogs`(`doctor_id`, `title`, `description`, `image_url`,`date`) VALUES (?,?,?,?,?)",
      [data.doctor_id, data.title, data.description, data.image_url, data.date],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        return callback(null, results);
      }
    );
  },

  getAllBlogs: (callback) => {
    pool.query("SELECT * FROM `blogs` order by ts DESC", [], (err, results) => {
      if (err) {
        return callback(err);
      }
      return callback(null, results);
    });
  },

  updateBlog: (data, callback) => {
    console.log("updating");
    pool.query(
      "UPDATE `blogs` SET `title`=?,`description`=?,`date`=?,`image_url` = ? WHERE doctor_id= ? AND title = ?",
      [
        data.title,
        data.description,
        data.date,
        data.image_url,
        data.doctor_id,
        data.old_blog_data.title,
      ],
      (err, results) => {
        if (err) {
          return callback(err);
        }
        return callback(null, results);
      }
    );
  },
};
