const cwd = process.cwd();
const db=require(cwd +'/config/db');

exports.tracking=(req,res)=>{
    res.render('user/tracking/tracking');
};