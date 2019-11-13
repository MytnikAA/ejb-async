<html>
<head>
    <script type="application/javascript">

        const asyncResponseWs = new WebSocket('ws://' + window.location.host + '/ejb-asyn/websocket/' + getCookie('uid'));
        asyncResponseWs.addEventListener('message', function (event) {
            console.log(event.data);

        });

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

        function createEchoCommand(input) {
            return {
                params: document.getElementById(input).value
            };
        }

        function getCookie(name) {

            const matches = document.cookie.match(new RegExp(
                "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : null
        }

    </script>
</head>
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

<br/>
<label for="log-input">Log: </label>
<input type="text" id="log-input"/>
<button onclick="doPost('api/log', createEchoCommand('log-input'))">
    asyncEcho via JMS
</button>
<br/>
<label for="echo-input">Echo: </label>
<input type="text" id="echo-input"/>
<button onclick="doPost('api/echo', createEchoCommand('echo-input'))">
    asyncEcho with response via WS
</button>
</body>
</html>
