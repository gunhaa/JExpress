const createApiDocs = () => {

    fetch("/api-docs/v1")
        .then(res => res.json())
        .then(res => {

            if(res.success){
                alert("성공 출력")
            }
        });
}

createApiDocs();