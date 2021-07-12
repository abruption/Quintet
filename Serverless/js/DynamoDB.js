var albumBucketName = 'articleimagess';

// Amazon Cognito 인증 공급자를 초기화합니다
AWS.config.region = 'us-east-1'; // 리전
AWS.config.credentials = new AWS.CognitoIdentityCredentials({
    IdentityPoolId: 'us-east-1:bd2930a0-14d0-4718-b85c-cd7567a7a91a',
});


var s3 = new AWS.S3({
    apiVersion: '2016-03-01',
    params: {Bucket: albumBucketName }
});

var datetime = new Date().toISOString();
var result = false;

function getCurrentDate(){
        var date = new Date();
        var year = date.getFullYear().toString();

        var month = date.getMonth() + 1;
        month = month < 10 ? '0' + month.toString() : month.toString();

        var day = date.getDate();
        day = day < 10 ? '0' + day.toString() : day.toString();

        var hour = date.getHours();
        hour = hour < 10 ? '0' + hour.toString() : hour.toString();

        var minites = date.getMinutes();
        minites = minites < 10 ? '0' + minites.toString() : minites.toString();

        var seconds = date.getSeconds();
        seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString();

        return year + '-' + month + '-' + day + ' ' + hour + ':' + minites + ':' + seconds;
}

function upload_to_db(img_location){
    var Writer = document.getElementById("Writer").value;
    var Title = document.getElementById("Title").value;
    var Content = document.getElementById("Content").value;
    

    var Item = {
        'article_id' : uuidv4(),
        'Writer': Writer,
        'Title' : Title,
        'Content' : Content,
        'img_source' : img_location,
        'createAt' : getCurrentDate(),
        'updateAt' : getCurrentDate(),
    }

    console.log(Item);

    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/board";

    fetch(URL, {
        method: "POST",
        headers: {
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            "TableName": "board",
            Item
        })
    }).then(alert("한줄평이 등록되었습니다."), location.href='/')
        .catch(err => console.log(err))
}

function add_article_with_photo(albumName) {
    var files = document.getElementById("Image").files;
    var Writer = document.getElementById("Writer").value;
    var Title = document.getElementById("Title").value;
    var Content = document.getElementById("Content").value;
    if(!files.length){
        return alert("사진 파일을 첨부해주세요.");
    }
    if(Writer == '' || Title == '' || Content == ''){
        return alert('빈 칸이 있는지 확인해주세요.');
    }

    var file = files[0];
    var fileName = file.name;
    var albumPhotosKey = encodeURIComponent(albumName) + "/";
    var albumPhotosKey = albumName + "/";

    var photoKey = albumPhotosKey + fileName;

    // Use S3 ManagedUpload class as it supports multipart uploads
    var upload = new AWS.S3.ManagedUpload({
        params: {
        Bucket: albumBucketName,
        Key: photoKey,
        Body: file
        }
    });
    
    var promise = upload.promise();

    let img_location;

    promise.then(
        function(data) {
        img_location = JSON.stringify(data.Location).replaceAll("\"","");
        upload_to_db(img_location);
        },
        function(err) {
            return alert("사진 업로드 중 오류가 발생하였습니다. : ", err.message);
        }
    );
}

function submitToAPI(e){
    e.preventDefault();
    add_article_with_photo('images');
}

function DeleteReview(e){
    e.preventDefault();

    var ID = document.getElementById('article_id').value;
    processDeleteReview(ID);
}

function processDeleteReview(ID){
    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/board";
    fetch(URL, {
        method: 'DELETE',
        mode: 'cors', // no-cors, cors, *same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json',
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrer: 'no-referrer', // no-referrer, *client
        body: JSON.stringify({
            "TableName": "board",
            "Key" : {
                "article_id": ID
            }
        })        
    }).then(alert('한줄평이 삭제 되었습니다.'), location.href='/') 
        .catch(err => alert(err + '\n오류가 발생하였습니다.'))
}

function loadContent(e){
    e.preventDefault();

    var Writer = document.getElementById("Writer").value;
    var Title = document.getElementById("Title").value;

    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/board";
    fetch(URL, {
        method: "GET",
        headers: {
            'Accept': 'application/json'
        }
    }).then(resp => resp.json())
    .then(function(data){
        let article_arr = data.Items;
        return article_arr.map(function(article_indiv){
            if(article_indiv.Writer == Writer && article_indiv.Title == Title){
                document.getElementById('Writer').disabled = true;
                document.getElementById('Title').disabled = true;
                return insertText(article_indiv.Content, article_indiv.article_id, article_indiv.createAt);
            }
            else
                result = true;
        })
    })
    if(result == true)
        return alert('입력하신 정보에 맞는 한줄평이 존재하지 않습니다.');
}

function insertText(Content, article_id, createAt){
    document.getElementById('Content').value = Content;
    document.getElementById('article_id').value = article_id;
    document.getElementById('createAt').value = createAt;
    alert('조회가 완료되었습니다.');
}

function EditContent(e){
    e.preventDefault();

    edit_article_with_photo('images');
}

function edit_article_with_photo(albumName){
    var files = document.getElementById("Image").files;
    
    if(!files.length){
        return alert("사진 파일을 첨부해주세요.");
    }

    var file = files[0];
    var fileName = file.name;
    var albumPhotosKey = encodeURIComponent(albumName) + "/";
    var albumPhotosKey = albumName + "/";

    var photoKey = albumPhotosKey + fileName;

    // Use S3 ManagedUpload class as it supports multipart uploads
    var upload = new AWS.S3.ManagedUpload({
        params: {
        Bucket: albumBucketName,
        Key: photoKey,
        Body: file
        }
    });
    
    var promise = upload.promise();

    let img_location;

    promise.then(
        function(data) {

        img_location = JSON.stringify(data.Location).replaceAll("\"","");
        edit_to_db(img_location);
        },
        function(err) {
        return alert("사진 업로드 중 오류가 발생하였습니다. : ", err.message);
        }
    );
}

function edit_to_db(img_location){

    var ID = document.getElementById('article_id').value;
    var Writer = document.getElementById('Writer').value;
    var Title = document.getElementById('Title').value;
    var Content = document.getElementById('Content').value;
    var createAt = document.getElementById('createAt').value;

    var Item = {
        'article_id' : ID,
        'Writer': Writer,
        'Title' : Title,
        'Content' : Content,
        'img_source' : img_location,
        'createAt' : createAt,
        'updateAt' : getCurrentDate(),
    }

    console.log(Item);

    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/board";

    fetch(URL, {
        method: "POST",
        headers: {
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            "TableName": "board",
            Item
        })
    }).then(alert("한줄평이 수정되었습니다."), location.href='/')
        .catch(err => console.log(err))
}

function getCurrentDate(){
    var date = new Date();
    var year = date.getFullYear().toString();

    var month = date.getMonth() + 1;
    month = month < 10 ? '0' + month.toString() : month.toString();

    var day = date.getDate();
    day = day < 10 ? '0' + day.toString() : day.toString();

    var hour = date.getHours();
    hour = hour < 10 ? '0' + hour.toString() : hour.toString();

    var minites = date.getMinutes();
    minites = minites < 10 ? '0' + minites.toString() : minites.toString();

    var seconds = date.getSeconds();
    seconds = seconds < 10 ? '0' + seconds.toString() : seconds.toString();

    return year + '-' + month + '-' + day + ' ' + hour + ':' + minites + ':' + seconds;
}

function upload_member_to_db(){
    var ID = document.getElementById("id").value;
    var Name = document.getElementById('name').value;
    var Email = document.getElementById('email').value;
    var Password = document.getElementById("password").value;
    

    var Item = {
        'ID' : ID,
        'Name': Name,
        'Password' : Password,
        'Email' : Email,
        'createAt' : getCurrentDate(),
    }

    console.log(Item);

    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/member";

    fetch(URL, {
        method: "POST",
        headers: {
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            "TableName": "member",
            Item
        })
        
    }).then(alert('회원가입에 성공하였습니다.'), location.href='/login') 
        .catch(err => console.log(err))
}

function onLogin(e){
    e.preventDefault();
    
    var ID = document.getElementById("ID").value;
    var Password = document.getElementById("Password").value;

    if(ID == '' || Password == '')
        alert('아이디 또는 비밀번호를 입력해주세요.');
    else {
        const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/member";
        fetch(URL, {
            method: "GET",
            headers: {
                'Accept': 'application/json'
            }
        }).then(resp => resp.json())
        .then(function(data){
            let article_arr = data.Items;
            return article_arr.map(function(article_indiv){
                if(article_indiv.ID == ID && article_indiv.Password == Password){
                    sessionStorage.setItem('name', article_indiv.Name);
                    sessionStorage.setItem('id', article_indiv.ID);
                    location.href='/';
                    return alert(article_indiv.Name +'님 환영합니다.');
                }
                else
                    result = true;
            })
        }) 
        if(result == true){
            location.href='/login';
            return alert('아이디 또는 비밀번호를 확인해주세요.');
        }
    }
}

function onJoin(e){
    e.preventDefault();

    var Name = document.getElementById('name').value;
    var Password = document.getElementById("password").value;
    var Password_check = document.getElementById("passwordcheck").value;

    if(document.getElementById('id').disabled == true){
        if(Name != '' && Password != '' && Password_check != ''){
            if(Password != Password_check){
                alert('비밀번호를 확인해주세요.')
            } else {
                upload_member_to_db();
            }
        } else{
            alert('입력되지 않은 값이 존재합니다.')
        }
    } else {
        alert('아이디 중복조회를 진행하지 않았습니다.');
    }
}

function idCheck(e){
    e.preventDefault();

    var id = document.getElementById("id").value;

    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/member";
    fetch(URL, {
        method: "GET",
        headers: {
            'Accept': 'application/json'
        }
    }).then(resp => resp.json())
    .then(function(data){
        let article_arr = data.Items;
        return article_arr.map(function(article_indiv){
            if(article_indiv.ID == id){
                location.reload();
                return alert('입력하신 ID는 현재 사용중입니다.\n다른 ID를 입력해주세요.');
            } else {    
                    return result = true;
                }
            })
        })

    if(result == true){
        if(confirm('사용할 수 있는 ID입니다.\n사용하시려면 확인을 눌러주세요.') == true){
            return document.getElementById('id').disabled = true;
        }
    }
}

function onLeave(e){
    e.preventDefault();

    var ID = document.getElementById('id').value;
    var Password = document.getElementById('password').value;
    var Password_Check = document.getElementById('passwordcheck').value;

    if(Password != Password_Check){
        location.href='/leave';
        return alert('입력하신 비밀번호가 일치하지 않습니다.');
    } else {
        const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/member";
        fetch(URL, {
            method: "GET",
            headers: {
                'Accept': 'application/json'
            }
        }).then(resp => resp.json())
        .then(function(data){
            let article_arr = data.Items;
            return article_arr.map(function(article_indiv){
                if(article_indiv.ID == ID && article_indiv.Password == Password){
                    return processLeave(ID);
                } else{
                    result = true;
                }
            })
        })

        if(result == true){
            location.href='/leave';
            alert('정확한 비밀번호를 입력해주세요.');
        }
    }
}

function processLeave(ID){
    const URL = "https://ypay7nz16k.execute-api.us-east-1.amazonaws.com/Beta/member";
    fetch(URL, {
        method: 'DELETE',
        mode: 'cors', // no-cors, cors, *same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json',
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: 'follow', // manual, *follow, error
        referrer: 'no-referrer', // no-referrer, *client
        body: JSON.stringify({
            "TableName": "member",
            "Key" : {
                "ID": ID
            }
        })        
    }).then(alert('회원탈퇴가 완료되었습니다.'), sessionStorage.clear(), location.href='/') 
        .catch(err => alert(err + '\n오류가 발생하였습니다.'))
}