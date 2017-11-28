try {
    var Spooky = require('spooky');
} catch (e) {
    var Spooky = require('../lib/spooky');
}

var spooky = new Spooky({
    child: {
        transport: 'http'
    },
    casper: {
        logLevel: 'debug',
        verbose: true,
    }
}, function (err) {
    if (err) {
        var e = new Error('Failed to initialize SpookyJS');
        e.details = err;
        throw e;
    }
    spooky.start('http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu4');
    spooky.then(function () {
        console.log('doit');
        var rows = Spooky.evaluate(document.querySelectorAll('font.list_title'));
        var titles = [];

        for (var i = 3; i<rows.length; i++) {
            var a = rows.innerHTML;
            var title = {};
            title['title'] = a.innerText;
            titles.push(title);
        }
        console.log("타이틀",titles)
    });
    spooky.run();
});

spooky.on('error', function (e, stack) {
    console.error(e);
    if (stack) {
        console.log(stack);
    }
});

spooky.on('console', function (line) {
    console.log(line);
});


spooky.on('hello', function (tweet) {
    console.log("데이터 : ", tweet);
});

spooky.on('log', function (log) {
    if (log.space === 'remote') {
        console.log(log.message.replace(/ \- .*/, ''));
    }
});
