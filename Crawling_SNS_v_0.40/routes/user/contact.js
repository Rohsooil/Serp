const cwd = process.cwd();
const express = require('express');
const router = express.Router();
const controller = require(cwd+'/controller/user/contact');
const passport = require(cwd+'/config/passport');

router.get('/notice',controller.notice_list);
router.get('/notice/:id',controller.notice_view);
router.get('/qna',controller.qna_list);
router.get('/qna/:id',controller.qna_view);

router.get('/qnawrite',passport.authenticate('jwt',{session:false}),controller.qna_list);
router.get('/question',passport.authenticate('jwt',{session:false}),controller.question_list);
router.get('/question_write',passport.authenticate('jwt',{session:false}),controller.question_write);
router.get('/question/:id',passport.authenticate('jwt',{session:false}),controller.question_view);

// 수정한 부분
// router.get('/noticeview',controller.notice_view);

module.exports=router;