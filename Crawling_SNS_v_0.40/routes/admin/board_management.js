const cwd=process.cwd();
const db=require(cwd+'/config/db');
const passport = require(cwd+'/config/passport');

const express = require('express');
const router = express.Router();
const controller = require(cwd+'/controller/admin/board_management');

// admin/board/~~

router.get('/admin_event',passport.authenticate('jwt',{session:false}),controller.event_list);
router.get('/admin_hotdeal',passport.authenticate('jwt',{session:false}),controller.hotdeal_list);

router.post('/admin_event/insert',passport.authenticate('jwt',{session:false}),controller.event_insert);
router.post('/admin_hotdeal/insert',passport.authenticate('jwt',{session:false}),controller.hotdeal_insert);

router.post('/admin_event/update/:id',passport.authenticate('jwt',{session:false}),controller.event_update);
router.post('/admin_hotdeal/update/:id',passport.authenticate('jwt',{session:false}),controller.hotdeal_update);

router.post('/admin_event/delete/:id',passport.authenticate('jwt',{session:false}),controller.event_delete);
//hotdeal delete
router.post('/admin_hotdeal',passport.authenticate('jwt',{session:false}),controller.hotdeal_delete);


module.exports = router;