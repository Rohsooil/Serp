const cwd = process.cwd();
const db=require(cwd +'/config/db');

exports.event_list=(req,res)=>{
  db.query('select * from notice',(err,results)=>{
    console.log(results);
    res.render('user/event/event',{notice:results});
  })
};

exports.event_view=(req,res)=>{
  const event_id=req.params.id;
  db.query('select * from event where Event_ID=?',[event_id],(err,results)=>{
    if(err) console.log(err);
    res.render('user/event/event',{event:results});
  })
};

exports.event_up=(req,res)=>{
  const event_id=req.params.id;
  //boardname은 정수로한다
  //event 0 hotdeal 1 free 2
  db.query('select count(*) as check from updown where User_ID=? and Board_Name=? and Board_ID=?',
    [req.user.User_ID,0,event_id],(err,result)=>{
    if(err) console.log(err);
    if(result[0].check>0){
      res.redirect('user/event/event'+event_id);
    }else{
      db.query('update event set Event_Score+=1 where Event_ID=?',[event_id],(err,result)=>{
        if(err) console.log(err);
        res.redirect('user/event/event'+event_id);
      });
    }
  });
};

exports.event_down=(req,res)=>{
  const event_id=req.params.id;
  //boardname은 정수로한다
  //event 0 hotdeal 1 free 2
  db.query('select count(*) as check from updown where User_ID=? and Board_Name=? and Board_ID=?',
    [req.user.User_ID,0,event_id],(err,result)=>{
    if(err) console.log(err);
    if(result[0].check>0){
      res.redirect('user/event/event'+event_id);
    }else{
      db.query('update event set Event_Score-=1 where Event_ID=?',[event_id],(err,result)=>{
        if(err) console.log(err);
        res.redirect('user/event/event'+event_id);
      });
    }
  });
};

exports.event_grade=(req,res)=>{
  const event_id = req.params.id;
  const user_id=req.user.User_ID;
  const event = 0;
  const {grade_score,grade_content} = req.body;
  db.query('insert into grade (User_ID,Board_Name,Board_ID,Grade_Score,Grade_Content) values(?,?,?,?,?)',
    [user_id,event,event_id,grade_score,grade_content],(err)=>{
    if(err) console.log(err);
    res.render('user/event/event'+event_id);
  })
};
// 댓글입력
exports.event_comment=(req,res)=>{
  const event_id =req.params.id;
  const comment = req.body.comment;
  db.query('insert into comment (User_ID,Board_Name,Board_ID,Comment_Content) values (?,?,?,?)',
    [req.user.User_ID,0,event_id,comment],(err)=>{
    if(err) console.log(err);
    res.redirect('user/event/event'+event_id);
  })
};
exports.event_comment_delete=(req,res)=>{
  const event_id = req.params.id;
  const comment_id = req.params.comment_id;
  const user_id=req.user.User_ID;
  db.query('delete from comment where User_ID=? and Comment_ID=?',
    [user_id,comment_id],(err)=>{
    if(err) console.log(err);
    res.render('user/event/event/'+event_id)
  });
};