const cwd = process.cwd();
const db=require(cwd+'/config/db');

exports.event_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select Event_ID,Event_Title from event', (err, results) => {
      if (err) console.log(err);
      res.render('admin/event/event', {event: results});
    })
  }
};

exports.event_view=(req,res)=>{
  const event_id=req.params.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select * from event where Event_ID=?',[event_id] ,(err, results) => {
      if (err) console.log(err);
      res.render('admin/event/event_view', {event: results});        //수정
    })
  }
};


exports.event_insert=(req,res)=>{
  const {Title,Link,Content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('insert into event (Event_Title,Event_Link,Event_Content) values (?,?,?)',
      [Title, Link, Content], (err) => {
        if (err) console.log(err);
        res.redirect('admin/event/event');       //수정
      })
  }
};

exports.event_update=(req,res)=>{
  const event_id=req.params.id;
  const {Title,Link,Content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('update event set Event_Title=? , Event_Link=?, Event_Content=? where Event_ID=?',
      [Title, Link, Content, event_id], (err) => {
        if (err) console.log(err);
        res.redirect('admin/event/event');
      })
  }
};

exports.event_delete=(req,res)=>{
  const event_id=req.params.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('delete from event where Event_ID=?', [event_id], (err) => {
      if (err) console.log(err);
      res.redirect('admin/event/event');
    })
  }
};

exports.hotdeal_list=(req,res)=>{
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select * from hotdeal', (err, results) => {
      if (err) console.log(err);
      res.render('admin/hotdeal/hotdeal', {hotdeal: results});
    })
  }
};

exports.hotdeal_view=(req,res)=>{
  const hotdeal_id=req.params.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('select * from hotdeal where Hotdeal_ID=?',[hotdeal_id], (err, results) => {
      if (err) console.log(err);
      res.render('admin/hotdeal/hotdeal', {hotdeal: results});         //수정
    })
  }
};


exports.hotdeal_insert=(req,res)=>{
  const {Title,Link,Content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('insert into hotdeal (Hotdeal_Title,Hotdeal_Link,Hotdeal_Content) values (?,?,?)',
      [Title, Link, Content], (err) => {
        if (err) console.log(err);
        res.redirect('admin/hotdeal/hotdeal');           //수정
      })
  }
};
exports.hotdeal_update=(req,res)=>{
  const hotdeal_id=req.params.id;
  const {Title,Link,Content}=req.body;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('update event set Hotdeal_Title=? , Hotdeal_Link=?, Hotdeal_Content=? where Hotdeal_ID=?',
      [Title, Link, Content, hotdeal_id], (err) => {
        if (err) console.log(err);
        res.redirect('admin/hotdeal/hotdeal');           //수정
      })
  }
};
exports.hotdeal_delete=(req,res)=>{
  const hotdeal_id=req.body.id;
  if(req.user.User_isAdmin===0){
    return res.render('main',{message:"관리자만 접속할 수 있습니다."})
  }else {
    db.query('delete from hotdeal where Hotdeal_ID=?', [hotdeal_id], (err) => {
      if (err) console.log(err);
      res.redirect('admin_hotdeal');         //수정
    })
  }
};