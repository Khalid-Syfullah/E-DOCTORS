const { uploadBlog, getAllBlogs, updateBlog } = require("./blogs.controller");

const router = require("express").Router();

router.post("/addBlog", uploadBlog);
router.get("/getAllBlogs", getAllBlogs);
router.post("/updateBlog", updateBlog);

module.exports = router;
