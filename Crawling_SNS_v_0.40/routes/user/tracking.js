const cwd = process.cwd();
const express = require('express');
const router = express.Router();
const controller = require(cwd+'/controller/user/tracking');
const passport = require(cwd+'/config/passport');

//tracking

router.get('/',controller.tracking);

//배송번호 입력
router.post('/insert',);
router.post('/delete/:id');

module.exports=router;