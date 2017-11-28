const cwd = process.cwd();
const db=require(cwd +'/config/db');

exports.hotdeal_list=(req,res)=>{
  db.query('select * from hotdeal',(err,results)=>{
    console.log(results);
    res.render('user/hotdeal/hotdeal',{hotdeal:results});
  })
};

exports.hotdeal_view=(req,res)=>{
  const hotdeal_id=req.params.id;
  db.query('select * from hotdeal where Hotdeal_ID=?',[hotdeal_id],(err,results)=>{
    if(err) console.log(err);
    res.render('user/hotdeal/hotdeal_click',{hotdeal:results});
  })
};

exports.hotdeal_up=(req,res)=>{
  const hotdeal_id=req.params.id;
  const hotdeal=1;
  //boardname은 정수로한다
  //hotdeal 0 hotdeal 1 free 2
  db.query('select count(*) as check from updown where User_ID=? and Board_Name=? and Board_ID=?',
    [req.user.User_ID,hotdeal,hotdeal_id],(err,result)=>{
      if(err) console.log(err);
      if(result[0].check>0){
        res.redirect('user/hotdeal/hotdeal'+Hotdeal_id);
      }else{
        db.query('update hotdeal set Hotdeal_Score+=1 where Hotdeal_ID=?',[hotdeal_id],(err,result)=>{
          if(err) console.log(err);
          res.redirect('user/hotdeal/hotdeal'+hotdeal_id);
        });
      }
    });
};

exports.hotdeal_down=(req,res)=>{
  const hotdeal_id=req.params.id;
  const hotdeal=1;
  //boardname은 정수로한다
  //hotdeal 0 hotdeal 1 free 2
  db.query('select count(*) as check from updown where User_ID=? and Board_Name=? and Board_ID=?',
    [req.user.User_ID,hotdeal,hotdeal_id],(err,result)=>{
      if(err) console.log(err);
      if(result[0].check>0){
        res.redirect('/user/hotdeal/hotdeal'+hotdeal_id);
      }else{
        db.query('update hotdeal set Hotdeal_Score-=1 where Hotdeal_ID=?',[hotdeal_id],(err,result)=>{
          if(err) console.log(err);
          res.redirect('user/hotdeal/hotdeal'+hotdeal_id);
        });
      }
    });
};

exports.hotdeal_grade=(req,res)=>{
  const hotdeal_id = req.params.id;
  const user_id=req.user.User_ID;
  const hotdeal = 1;
  const {grade_score,grade_content} = req.body;
  db.query('insert into grade (User_ID,Board_Name,Board_ID,Grade_Score,Grade_Content) values(?,?,?,?,?)',
    [user_id,hotdeal,hotdeal_id,grade_score,grade_content],(err)=>{
      if(err) console.log(err);
      res.render('user/hotdeal/hotdeal'+hotdeal_id);
    });
};
// 댓글입력
exports.hotdeal_comment=(req,res)=>{
  const hotdeal_id =req.params.id;
  const comment = req.body.comment;
  const hotdeal =1;
  db.query('insert into comment (User_ID,Board_Name,Board_ID,Comment_Content) values (?,?,?,?)',
    [req.user.User_ID,hotdeal,hotdeal_id,comment],(err)=>{
      if(err) console.log(err);
      res.redirect('user/hotdeal/hotdeal'+hotdeal_id);
    })
};
exports.hotdeal_comment_delete=(req,res)=>{
  const hotdeal_id = req.params.id;
  const comment_id = req.params.comment_id;
  const user_id=req.user.User_ID;
  db.query('delete from comment where User_ID=? and Comment_ID=?',
    [user_id,comment_id],(err)=>{
      if(err) console.log(err);
      res.render('user/hotdeal/hotdeal'+hotdeal_id)
    });
};