function EvenOrOdd(number){
	return new Promise((resolve, reject) => { 
        if(number % 2 === 0){
            resolve('Even Number')
        }else{
            reject('Odd Number')
		}        
    })
}

EvenOrOdd(5)
    .then(resolve =>  console.log(resolve))
    .then().catch((err) =>  console.log(err))