const { uploadBlog, getAllBlogs, updateBlog } = require("./blogs.service");
const fs = require("fs");

module.exports = {
  uploadBlog: (req, res) => {
    console.log("uploading");
    req.body.date = new Date().toLocaleDateString();
    var image = req.body.image;
    var image_url =
      "images/blogs/" + req.body.doctor_id + req.body.title + ".jpeg";
    fs.writeFile(image_url, image, { encoding: "base64" }, (err) => {
      if (err) {
        console.log(err);
        return res.status(500);
      }
      req.body.image_url = image_url;

      uploadBlog(req.body, (err, result) => {
        if (err) {
          return res.status(500);
        }
        return res.status(200).json({ serverMsg: "success" });
      });
    });
  },
  getAllBlogs: (req, res) => {
    getAllBlogs((err, results) => {
      if (err) {
        return res.status(500);
      }
      return res.status(200).json(results);
    });
  },

  updateBlog: (req, res) => {
    console.log("updating");
    req.body.date = new Date().toLocaleDateString();
    var image = req.body.image;
    var image_url =
      "images/blogs/" +
      req.body.doctor_id +
      req.body.old_blog_data.title +
      ".jpeg";
    fs.unlink(image_url, (err) => {
      if (err) {
        console.log(err);
      }
    });

    image_url = "images/blogs/" + req.body.doctor_id + req.body.title + ".jpeg";
    fs.writeFile(image_url, image, { encoding: "base64" }, (err) => {
      if (err) {
        console.log(err);
        return res.status(500);
      }
      req.body.image_url = image_url;

      updateBlog(req.body, (err, result) => {
        if (err) {
          return res.status(500);
        }
        return res.status(200).json({ serverMsg: "success" });
      });
    });
  },
};
