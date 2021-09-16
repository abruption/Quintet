const fs = require('fs')
console.log('Start')
fs.readFile('./text.txt', 'utf8', (err, result) => {
    if(err) throw err
    else console.log(result)
})
console.log('end')