const cwd = process.cwd();
const jwt = require('jsonwebtoken');
const db = require(cwd+'/config/db');
const bkfd2Password = require("pbkdf2-password");
const hasher = bkfd2Password();
const config = require(cwd+'/config/secret');
const nodemailer = require(cwd + '/config/email');
const crypto=require('crypto');

const email_template=require(cwd+'/config/email_template');

exports.signin=(req,res)=>{
  //TODO : 패스워드 중복검사
  //이메일 패스워드 이름을 받는다
  const {email,password,password_verify,name}= req.body;
  if(password!==password_verify) res.render('main',{message:"패스워드 중복"});
  //중복된 이메일 검사
  db.query('select count(*) as checkuser from user where User_Email=?',[email],(err,result)=>{
    if(err) console.log(err);
    // 결과값이 있다면
    if(result[0].checkuser>0){
      //TODO : 로그인 실패시 이동하는 화면(메인화면)에 어떤 상태인지 표기
      res.render('main',{message:"중복된 아이디"})
    }else{
      hasher({password:password},(err,pass,salt,hash)=>{
        crypto.randomBytes(64,(err,buf)=>{
          if(err) console.log(err);
          const user = {
            authID:buf.toString('hex'),
            User_Email:email,
            User_Name: name,
            User_Hash: hash,
            User_Salt: salt
          };
          console.log(user);
          db.query('insert into user set ?',[user],(err,results)=>{
            if(err) console.log(err);
            //유저 이메일 인증
            let email_token = jwt.sign({user_email: email},config.secret,{expiresIn:'5h'});
            res.render('main',{message: "회원가입을 완료하시려면 이메일 등록을 해주세요"});
            nodemailer.sendMail(email_template.email_verify(email,email_token));
          })
        })
      })
    }
  })
};

exports.login=(req,res)=>{
  const {email,password} = req.body;
  db.query('select * from user where User_Email=?',[email],(err,result)=>{
    if(err) console.log(err);
    const user = result[0];
    console.log(user);
    if(!user){
      return res.render('main',{message:"아이디나 비밀번호가 틀렸습니다"});
    }else if(user.User_Email_Verify===1){
      hasher({password:password,salt:user.User_Salt},(err,pass,salt,hash)=>{
        if(hash === user.User_Hash){
          //로그인 완료
          const token = jwt.sign({authID:user.authID},config.secret,{expiresIn:'1h'});
          res.cookie('jwt',token);
          //TODO:오버뷰 화면으로 이동
          if(user.User_isAdmin===1){
            res.render('admin/overview');
          }else{
            console.log('하이');
            user.User_isAdmin=1;
            // res.render('user/overview/overview');
            res.render('admin/overview');
          }
        }else{
          //비밀번호 틀림
          //TODO : 로그인 창으로 이동
          res.render('main',{message:"아이디나 비밀번호가 틀렸습니다"})
        }
      })
    }else{
      res.render('main',{message:"이메일 인증을 해주세요"});
    }
  });
};

exports.email_verify=(req,res)=>{
  //TODO : 토큰을 받아서 유효기간 검사하고 유효하다면 email찾고 email있으면 1로 바꾸고 true
  //TODO : GET으로 토큰받기
  const token = req.query;
  jwt.verify(token,config.secret,(err,decoded)=>{
    if(err){
      console.log(err);
      res.render('main',{message:"잘못되거나 만료된 토큰입니다"});
    } else {
      db.query('select * from user where user_email=?',[decoded.email],(err,result)=>{
        if(err) console.log(err);
        if(result[0]){
          db.query('update user set User_Email_Verify=1 where User_Email=?',[decoded.email],(err,results)=>{
            if(err) console.log(err);
            if(results.changedRows===0){
              res.render('main',{message:"이미 인증된 이메일 입니다."});
            }else if(results.changedRows===1){
              res.render('main',{message:"인증되었습니다. 로그인해주세요"});
            }
          })
        }else{
          res.render('main',{message:"잘못된 토큰입니다."})
        }
      })
    }
  });
};

exports.find_pw=(req,res)=>{
  const email = req.body.email;
  db.query('select count(*) as member_exist from member where User_Email=?',[email],(err,result)=>{
    if(err) console.log(err);
    if(result[0].member_exist ===0){
      res.render('main',{message:"없는 회원입니다"});
    }else{
      const token = jwt.sign({User_Email:email},config.secret,{expiresIn:'120m'});
      res.render('main',{message:'비밀번호 재설정 링크를 보냈습니다. 메일함을 확인해보세요'});
      nodemailer.find_pw(email,token);
    }
  })
};
//새비밀번호 찾기 & 비밀번호 변경
exports.new_pw=(req,res)=>{
  const {password,token} =req.body;
  jwt.verify(token,config.secret,(err,decoded)=>{
    if(err) res.render('main',{message:"토큰이 만료되었거나 유효한 토큰이 아닙니다. 다시 시도해 주십시오"});
    else{
      hasher({password:password},(err,pass,salt,hash)=>{
        const user = {
          User_Hash: hash,
          User_Salt: salt
        };
        db.query('update user set ? where User_Email=?',[user,decoded.User_Email],(err,results)=>{
          if(err) console.log(err);
          if(results.changedRows===1) {
            res.render('main', {message: "성공적으로 변경되었습니다. 로그인해주세요"});
          }else{
            res.render('main', {message: "비밀번호가 변경되지 않았습니다."});
          }
        })
      })
    }
  })
};