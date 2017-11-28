const cwd = process.cwd();
const express = require('express');
const router = express.Router();
const passport = require(cwd+'/config/passport');
const controller = require(cwd+'/controller/admin/account_management');

router.get('/admin_overview',controller.overview);
//account
router.get('/');
router.post('/new_pw',passport.authenticate('jwt',{session:false}),controller.new_pw);
router.get('/admin_settings',passport.authenticate('jwt',{session:false}),controller.admin_settings);
router.get('/user_list',passport.authenticate('jwt',{session:false}),controller.account_list);
router.get('/report/comment/:id',passport.authenticate('jwt',{session:false}),controller.account_comment_list);

router.post('/ban/:id',passport.authenticate('jwt',{session:false}),controller.account_ban);

//experiment

// router.get('/account/update/email/:id',);

module.exports = router;