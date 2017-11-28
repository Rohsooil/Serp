const cwd=process.cwd();
const express = require('express');
const router = express.Router();
const controller=require(cwd+'/controller/admin/contact');
const passport = require(cwd+'/config/passport');

router.get('/admin_notice',passport.authenticate('jwt',{session:false}),controller.notice_list);
//notice_delete
router.post('/admin_notice',passport.authenticate('jwt',{session:false}),controller.notice_delete);
router.get('/admin_notice/insert',passport.authenticate('jwt',{session:false}),controller.notice_insert);
router.post('/admin_notice_write',passport.authenticate('jwt',{session:false}),controller.notice_insert);
router.get('/admin_notice/update/:id',passport.authenticate('jwt',{session:false}),controller.notice_update);
router.get('/admin_notice/delete/:id',passport.authenticate('jwt',{session:false}),controller.notice_delete);

router.get('/admin_question/form',passport.authenticate('jwt',{session:false}),controller.question_form);
router.get('/admin_question',passport.authenticate('jwt',{session:false}),controller.question_list);
router.get('/admin_question/insert',passport.authenticate('jwt',{session:false}),controller.question_answer_insert);

router.get('/admin_qna',passport.authenticate('jwt',{session:false}),controller.qna_list);
router.get('/admin_qna/:id',passport.authenticate('jwt',{session:false}),controller.qna_view);
router.get('/admin_qna/insert',passport.authenticate('jwt',{session:false}),controller.qna_insert);
router.get('/admin_qna/update/:id',passport.authenticate('jwt',{session:false}),controller.qna_update);
router.get('/admin_qna/delete/:id',passport.authenticate('jwt',{session:false}),controller.qna_delete);
router.get('/admin_qna_form',passport.authenticate('jwt',{session:false}),controller.qna_form);

router.get('/admin_notice/:id',passport.authenticate('jwt',{session:false}),controller.notice_list);
router.get('/admin_notice_write',passport.authenticate('jwt',{session:false}),controller.notice_write);
module.exports = router;