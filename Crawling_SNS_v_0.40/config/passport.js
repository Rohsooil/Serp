const db = require('./db');
const config = require('./secret');
const passport = require('passport');
const passportJWT = require('passport-jwt');
const JwtStrategy = passportJWT.Strategy;

const cookieExtractor = (req) => {
  let token = null;
  if (req && req.cookies) {
    token = req.cookies['jwt'];
  }
  return token;
};
const opts = {};
opts.jwtFromRequest=cookieExtractor;
opts.secretOrKey =config.secret;

passport.use('jwt', new JwtStrategy(opts,(jwt_payload,done)=>{
  let user =null;
  db.query('select User_ID,authID,User_Email,User_Name,User_Date from user where authID=?',[jwt_payload.authID],(err,results)=>{
    if(err) console.log(err);
    user = results[0];
    if(user) done(null, user);
    else done(null, false);
  })
}));

module.exports=passport;