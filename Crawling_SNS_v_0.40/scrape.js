var target = "http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4"; // Our target URL

var casper = require('casper').create({
    verbose: true,
    logLevel: "info",
    pageSettings: {
        webSecurityEnabled: false,  // (http://casperjs.readthedocs.org/en/latest/faq.html#i-m-having-hard-times-downloading-files-using-download)
        userAgent: "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11" // Spoof being Chrome on a Mac (https://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx)
    }
});

casper.start(target);
casper.then(()=>{
    var rows = document.querySelectorAll('font.list_title');
    var titles = [];

    for (var i = 3; i<rows.length; i++) {
        var a = rows.innerHTML;
        var title = {};
        title['title'] = a.innerText;
        titles.push(title);
    }
    console.log(titles);
});
casper.run();