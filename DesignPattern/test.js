function doSomething(number, callback) {
    callback(number * number)
}

function getSomething(result){
    console.log(`결과 값: ${result}`)
}

doSomething(5, getSomething)