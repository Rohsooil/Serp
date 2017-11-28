const cwd = process.cwd();
const db=require(cwd +'/config/db');

exports.overview=(req,res)=>{
  db.query('select * from notice',(err,results)=>{
    console.log(results);
    res.render('user/overview/overview',{notice:results});
  })
};