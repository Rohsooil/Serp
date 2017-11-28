const cwd = process.cwd();
const db=require(cwd+'/config/db');

exports.report=(req,res)=>{
  const user_id=req.body.user_id;
  const report_content=req.body.report_content;
  db.query('insert into report(User_ID,Report_Content) values (?,?)',[user_id,report_content],(err)=>{
    if(err) console.log(err);
    res.redirect('blabla');
  })
};