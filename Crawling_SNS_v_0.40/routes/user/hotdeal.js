const cwd = process.cwd();
const express = require('express');
const router = express.Router();
const controller = require(cwd+'/controller/user/hotdeal');
const passport = require(cwd+'/config/passport');


//hotdeal

router.get('/',passport.authenticate('jwt',{session:false}),controller.hotdeal_list);
router.get('/:id',passport.authenticate('jwt',{session:false}),controller.hotdeal_view);

router.post('/up/:id',passport.authenticate('jwt',{session:false}),controller.hotdeal_up);
router.post('/down/:id',passport.authenticate('jwt',{session:false}),controller.hotdeal_down);
router.post('/grade',passport.authenticate('jwt',{session:false}),controller.hotdeal_grade);

router.post('/comment',passport.authenticate('jwt',{session:false}),controller.hotdeal_comment);
router.post('/comment/delete/:id',passport.authenticate('jwt',{session:false}),controller.hotdeal_comment_delete);

module.exports=router;