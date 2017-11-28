const cwd = process.cwd();
const express = require('express');
const router = express.Router();
const controller = require(cwd+'/controller/user/freetalk');
const passport = require(cwd+'/config/passport');

router.get('/freetalk',controller.freetalk);
module.exports=router;