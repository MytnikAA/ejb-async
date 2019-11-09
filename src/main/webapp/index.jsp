<html>
<script type="application/javascript">
    function doPost(path, payload) {
        fetch(
            path,
            {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            }
        )
            .then(function (response) {
                console.log(response.status);
                if (response.status === 200) {
                    response.json()
                        .then(function (body) {
                            console.log('text response ', body);
                            var responseObject = JSON.parse(body);
                            console.log('json parsed response ', responseObject);
                        });
                }
            })
    }

    function doGet(path) {
        fetch(path)
            .then(function (response) {
                console.log(response.status);
                response.text()
                    .then(function (body) {
                        console.log(body);
                    })
            })
    }

    function createEchoCommand() {
        return {
            params: document.getElementById('echo-input').value
        };
    }

</script>
<body>
<h2>Hello World!</h2>
<button onclick="doGet('api/ping')">
    jdi
</button>

<button onclick="doGet('api/asyncVoidPing')">
    asyncVoidPing
</button>
<button onclick="doGet('api/asyncPing')">
    asyncPing
</button>

<label for="echo-input">Echo: </label>
<input type="text" id="echo-input"/>
<button onclick="doPost('api/echo', createEchoCommand())">
    asyncEcho via JMS
</button>
</body>
</html>
