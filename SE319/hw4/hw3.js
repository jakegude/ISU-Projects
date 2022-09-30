var rs = require('readline-sync');

var fNum1 = rs.question('1st Number: ');
var fNum2 = rs.question('2nd Number: ');
var fNum3 = rs.question('3rd Number: ');
var fNum4 = rs.question('4th Number: ');

var factorial = function (n) {
    if (n === 1) { return 1; }
    return n * factorial(n - 1);
}

var sumDigits = function () {
    var sum = 0;
    for (var i = 0; i < fNum2.length; i++) {
        sum += eval(fNum2.charAt(i));
    }
    return sum;
}

var reverse = function () {
    var r = "";
    for (var i = fNum3.length - 1; i > -1; i--) {
        r += fNum3.charAt(i);
    }
    return r;
}

var palindrome = function() {
    var p = true;
    
    for (var i = 0; i < fNum4.length - 1; i++) {
        if (fNum4.charAt(i) != fNum4.charAt(fNum4.length - 1 -i)) {
            p = false;
        }
    }
   return p;
}

console.log("The factorial of " + fNum1 + " is: " + factorial(fNum1));
console.log("The sum of the digits in " + fNum2 + " is: " + sumDigits());
console.log("The reverse of " + fNum3 + " is: "+ reverse());
console.log("Is " + fNum4 + " a palindrome? " + palindrome());



