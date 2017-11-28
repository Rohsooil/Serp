exports.email_verify = (email,token) =>{
  return {
    from: 'jjhh3079@gmail.com',
    to : email,
    subject: "Crawling SNS 이메일을 인증해주세요",
    text : "링크를 클릭하여 인증해주세요 : " + "https://blabla/email_verify?token=" + token
  };
};
exports.find_pw = (email,token) =>{
  return {
    from: 'jjhh3079@gmail.com',
    to : email,
    subject: "Crawling SNS 이메일을 인증해주세요",
    text : "비밀번호를 다시 설정하려면 링크를 클릭하세요 : " + "https://blabla/new_pw?token=" + token
  };
};