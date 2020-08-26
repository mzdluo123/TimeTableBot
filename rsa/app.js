const express = require("express");
const RSAUtils = require("./security");
var bodyParser = require('body-parser');

const app = express();
app.use(bodyParser());


app.get("/", (request, response) => {

    response.status(404);
});


app.post("/encode", (request, response) => {
    let {e, d, m, data} = request.body
    const key = RSAUtils.getKeyPair(String(e), "", String(m))
    response.send({"encoded": RSAUtils.encryptedString(key, data)})
});


app.post("/decode", (request, response) => {
    let {e, d, m, data} = request.body
    const key = RSAUtils.getKeyPair("", d, String(m))
    response.send({"decoded": RSAUtils.decryptedString(key, data)})
});


const listener = app.listen(6562, () => {
    console.log("Your app is listening on port " + listener.address().port);
});