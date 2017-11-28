const cwd=process.cwd();
const express = require('express');
const router = express.Router();
const passport = require(cwd+'/config/passport');
const controller = require(cwd+'/controller/user/overview');

router.get('/',controller.overview);

module.exports = router;