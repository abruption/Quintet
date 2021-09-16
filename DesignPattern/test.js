// 익명의 함수를 사용하는 콜백함수 예제
let temp = [1, 2, 3, 4, 5]
temp.forEach(x => {console.log(x * 2)})

// 함수의 이름과 값을 넘기는 콜백 함수
function doSomething(number, callback) {
    callback(number * number)
}

function getSomething(result){
    console.log(`결과 값: ${result}`)       // 결과 값: 5
}

doSomething(5, getSomething)