const cwd = process.cwd();
const db=require(cwd +'/config/db');

exports.notice_list=(req,res)=>{
  db.query('select Notice_ID,Notice_Title,Notice_Date from notice',(err,results)=>{
    if(err) console.log(err);
    res.render('user/contact/notice',{notice:results});
  })
};
exports.notice_view=(req,res)=>{
  const {notice_id}=req.body;
  db.query('select * from notice where Notice_ID=?',[notice_id],(err,results)=>{
    if(err) console.log(err);
    res.render('user/contact/notice_view',{notice:results});
  })
};
exports.qna_list=(req,res)=>{
  db.query('select Qna_ID,Qna_Title,Qna_Date from qna',(err,results)=>{
    res.render('user/contact/qna',{qna:results});
  })
};
exports.qna_view=(req,res)=>{
  const {qna_id}=req.body;
  db.query('select * from qna where Qna_ID=?',[qna_id],(err,results)=>{
    if(err) console.log(err);
    res.render('user/contact/qna_view',{qna:results});
  })
};
// TODO : answer 처리
exports.question_list=(req,res)=>{
  console.log(req.user);
  db.query('select Question_ID,Question_Title,Question_Date from question where User_ID=?',[req.user.User_ID],(err,results)=>{
    res.render('user/contact/question',{question:results});
  })
};
exports.question_view=(req,res)=>{
  const {question_id,user_id}=req.body;
  db.query('select * from question where Question_ID=? and User_ID=?',[question_id,user_id],(err,results)=>{
    if(err) console.log(err);
    res.render('user/contact/question_view',{question:results});
  })
};

exports.question_write=(req,res)=>{
    const {question_id,user_id}=req.body;
    db.query('select * from question where Question_ID=? and User_ID=?',[question_id,user_id],(err,results)=>{
        if(err) console.log(err);
        res.render('user/contact/questionwrite');
    })
};

// 수정할 부분
// exports.notice_view=(req,res)=>{
//     console.log('connect!');
//     db.query('select * from notice(Notice_Title,Notice_Content) values (?,?)',[res.body.notice_title,res.body.notice_content],()=>{
//       console.log('notice nth table inserted');
//       console.log('');
//     });
// };