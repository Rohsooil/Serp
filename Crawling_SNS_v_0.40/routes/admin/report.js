const cwd=process.cwd();
const express = require('express');
const router = express.Router();
const controller=require(cwd+'/controller/admin/report');
const passport = require(cwd+'/config/passport');

router.get('/admin/report',passport.authenticate('jwt',{session:false}),controller.report_list);
module.exports = router;