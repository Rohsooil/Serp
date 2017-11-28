const cwd = process.cwd();
const db=require(cwd+'/config/db');

exports.overview=(req,res)=>{
  res.render('admin/overview',{});
}

exports.admin_settings=(req,res)=>{
  res.render('admin/settings/settings',{});
}

exports.new_pw=(req,res)=>{
  const password = req.body.password;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    hasher({password: password}, (err, pass, salt, hash) => {
      const user = {
        User_Hash: hash,
        User_Salt: salt
      };
      db.query('update user set ? where Auth_ID=?', [user, decoded.User_Email], (err, results) => {
        if (err) console.log(err);
        if (results.changedRows === 1) {
          res.render('main', {message: "성공적으로 변경되었습니다. 로그인해주세요"});
        } else {
          res.render('main', {message: "비밀번호가 변경되지 않았습니다."});
        }
      })
    })
  }
};

exports.account_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select User_ID,User_Email,User_Name,User_Date,User_Blacklist from user', (err, results) => {
      if (err) console.log(err);
      res.render('admin/report/user_list', {account: results});
    })
  }
};
exports.account_comment_list=(req,res)=>{
  const user_id=req.params.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select user.User_ID,user.User_Name,comment.Comment_ID,comment.Comment_Content from user,comment where user.User_ID=?', [user_id], (err, results) => {
      if (err) console.log(err);
      res.render('admin/report/report', {account_comment: results});           //수정
    })
  }
};

exports.account_ban=(req,res)=>{
  const user_id=req.params.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('update user set User_Blacklist=1 where User_ID=?', [user_id], (err, result) => {
      if (err) console.log(err);
      res.redirect('admin/report/report');
    })
  }
};