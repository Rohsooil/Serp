const cwd=process.cwd();
const express = require('express');
const router = express.Router();
const controller=require(cwd+'/controller/user/report');
const passport = require(cwd+'/config/passport');

router.post('/:board/report',passport.authenticate('jwt',{session:false}),controller.report);

module.exports = router;