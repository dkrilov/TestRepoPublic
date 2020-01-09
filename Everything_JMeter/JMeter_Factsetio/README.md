# JMeter on factset.io

Test application using JMeter on factset.io

## Status

- WIP! Not ready for general use. This is a seed for further development.
- Java and JMeter installs inside the container.
- Successfully runs `jmeter-server --version`
- Run a simple test against [open.factset.com](https://open.factset.com)

## Next Steps
- Setup a simple node.js `web` process using node.js
  - Redis client for messaging queue system. Sending/Receiving data to all workers.
  - Dashboard 
    - Upload Test Scripts to Swift.
    - Commands (e.g. Start/Stop/Shutdown/Aggregate Results).
    - Pipe Logs to Stdout.
    - Collect data from all test files and merge into a single results file.
    - Generate report from test results.
    - Link directly to generated reports.
  - API for all functions as mentioned above
  - WebSocket for bidirectional communication between the browser and dashboard app
- Setup a `worker` process using node.js
  - Start/Stop/Shutdown of JMeter Test via default port 4445
  - Redis for message queue between web and worker processes
  - Swift for permanent storage of test scripts, logs, results and reports
  - Should notify Redis of test start/stop/finish
  - Status updates should be sent to stdout and captured in CLP log stream
  - Think about getting load testing samples into CLP and analyse data in Kibana or [Grafana](https://grafana.com/dashboards/1152)
- [Caching the build pack](https://devcenter.heroku.com/articles/buildpack-api#caching) 

## Advantages

- Easily control the number of worker containers to scale the test.
- Inbuilt metrics to measure load of the workers and overall throughput.
- No network restrictions to neighbouring factset.io apps.
- Click to deploy to any project or pipeline.
- Easily deploy to multiple regions.

## Technology

- Using pub/sub architecture we can setup communication channels between the Application and Workers
- Redis has support for pub/sub
- [AutobahnJS](https://crossbar.io/autobahn/) may be a good option for simplicity. ([GitHub](https://github.com/crossbario/autobahn-js))
- [JMeter Listener Plugin](https://github.com/delirius325/jmeter-elasticsearch-backend-listener) to send data to elastic search/kibana/grafana for analysis/realtime status

## Usage

👉 [Click here to deploy to factset.io](http://factset.io/deploy?template=https://github.factset.com/abarker/jmeter) ☁

## Copyright

JMeter is licensed under Apache License v2.0
