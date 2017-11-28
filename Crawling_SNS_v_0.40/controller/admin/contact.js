const cwd = process.cwd();
const db=require(cwd+'/config/db');

exports.question_form=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    res.render('admin/contact/questionwrite');
  }
}

exports.qna_form=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    res.render('admin/contact/qnawrite');
  }
};

exports.notice_write=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    console.log('1111111');
    res.render('admin/contact/noticewrite');
}
};
//////위에 수정 부분
exports.notice_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select Notice_ID,Notice_Title,Notice_Date from notice', (err, results) => {
      if (err) console.log(err);
      res.render('admin/contact/notice', {notice: results});
    })
  }
};

exports.notice_insert=(req,res)=>{
  const {notice_title,notice_content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('insert into notice (Notice_Title,Notice_Content) values (?,?)', [notice_title, notice_content], (err) => {
      if (err) console.log(err);
      res.redirect('admin_notice');
    })
  }
};
exports.notice_update=(req,res)=>{
  const notice_id = req.params.id;
  const {notice_title,notice_content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('update notice set Notice_Title=?,Notice_Content=? where Notice_ID=?', [notice_title, notice_content, notice_id], (err, result) => {
      if (err) console.log(err);
      res.redirect('admin/contact/notice');           //업데이트할것
    })
  }
};
exports.notice_delete=(req,res)=>{
  const notice_id= req.body.id;
  console.log(String(notice_id));
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('delete from notice where Notice_ID=?', [notice_id], (err, result) => {
      if (err) console.log(err);
      res.redirect('admin_notice');
    })
  }
};
exports.qna_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select Qna_ID,Qna_Title,Qna_Content,Qna_Date from qna', (err, results) => {
      if (err) console.log(err);
      res.render('admin/contact/qna', {qna: results});
    })
  }
};

exports.qna_view=(req,res)=>{
    const qna_id=req.params.id;
    if(req.user.User_isAdmin===0){
        return res.render('main',{message:"관리자만 접속할 수 있습니다."})
    }else {
        db.query('select * from qna where Qna_ID=?',[qna_id],(err,results)=>{
            if(err) console.log(err);
            res.render('admin/contact/qna',{qna:results});
        })
    }
};

// Qna_ID`, `Qna_Title`, `Qna_Content`, `Qna_Date`
exports.qna_insert=(req,res)=>{
  const {qna_title,qna_content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('insert into qna (Qna_Title,Qna_Content) values (?,?)', [qna_title, qna_content], (err) => {
      if (err) console.log(err);
      res.redirect('admin/contact/qnawrite');
    })
  }
};

exports.qna_update=(req,res)=>{
  const {qna_id,qna_title,qna_content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('update qna set Qna_Title=?,Qna_Content=? where Qna_ID=?', [qna_title, qna_content, qna_id], (err, result) => {
      if (err) console.log(err);
      res.redirect('admin/contact/qna');
    })
  }
};

exports.qna_delete=(req,res)=>{
  const {qna_id}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else{
    db.query('delete from qna where Qna_ID=?',[qna_id],(err,result)=>{
      if(err) console.log(err);
      res.redirect('admin/contact/qna');
    })
  }
};

exports.question_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
     return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else{
    db.query('select * from question where Question_ID_Answer=null',(err,results)=>{
      if(err) console.log(err);
      res.render('admin/contact/question',{question:results});
    })
  }
};

exports.question_answer_insert=(req,res)=>{
  const {question_id,question_title,question_content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('insert into question (Question_Title,Question_Content,Question_ID_Answer) values (?,?,?)', [question_title, question_content, question_id], (err) => {
      if (err) console.log(err);
      res.redirect('admin/contackt/question');
    })
  }
};