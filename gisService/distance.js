#!/usr/bin/env node;
import express from "express";
import timestamp from "time-stamp";
import { all, get, spread } from "axios";
const argv = require("yargs")
    .usage("Usage: $0 -a [env | arg] [name] -p [port]")
    .alias("a", "api")
    .nargs("a", 2)
    .describe("a", "The Graphhopper Distance Matrix API key")
    .demandOption("a")
    .alias("p", "port")
    .nargs("p", 2)
    .describe("p", "The server port is 7778")
    .default("p", 7778)
    .help("h")
    .alias("h", "help").argv;

const API_KEY = argv.api[1];
argv.api[0] === "env" ? process.env[argv.api[1]] : argv.api[1];
const PORT = argv.port;
const app = express();

app.set("json spaces", 4);
const log = (message) => {
    console.log(`${timestamp("[YYYY/MM/DD HH:mm:ss]")} - ${message}`);
};

const response =  (err, distance = 0, message = "") => ({
    message: err ? err : message,
    distance: distance
});

app.get("/getDistance", (req, res) => {
    log("key " + API_KEY);
    const { from, to } = req.query;
    log(`[Request] From: ${from}, To: ${to}`);
    if (!from) {
        log("[Response] Missing [from] parameter");
        res.status(400).json(response("Missing [from] parameter"));
    }
    if (!to) {
        log("[Response] Missing [to] parameter");
        res.status(400).json(response("Missing [to] parameter"));
    }

    all([
        get(`https://graphhopper.com/api/1/geocode?q=${from}&key=${API_KEY}`),
        get(`https://graphhopper.com/api/1/geocode?q=${to}&key=${API_KEY}`)
    ]).catch((err) => {
        log(`${err}`)
        res.status(400).json(response(`${err.response.data.message}`));
    }).then(spread((fromRes, toRes) => {
        var fromLat = fromRes.data.hits[0].point.lat;
        var fromLng = fromRes.data.hits[0].point.lng;
        var toLat = toRes.data.hits[0].point.lat;
        var toLng = toRes.data.hits[0].point.lng;
        get(`https://graphhopper.com/api/1/matrix?point=${fromLat},${fromLng}&point=${toLat},${toLng}&type=json&debug=true&out_array=distances&key=${API_KEY}`
        ).catch((err) => {
            log(`[ERROR] ${err}`);
            res.status(400).json(response(`[ERROR] ${err}`));
        }).then((response) => {
            return response.data.distances[0][1];
        }).then(function (distance) {
            res.status(200).json(response(`Distance: From: ${from}, To: ${to}`, distance));
        });
    }));
});

app.get("*", (req, res) => {
    res.status(404).json({
        message: "Not found"
    });
});

app.listen(PORT, () => {
    console.log(`\nDistance service listening on port ${PORT}`);
});
