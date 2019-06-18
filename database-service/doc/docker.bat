docker run -d --name=grafana -p 3000:3000 grafana/grafana
docker run -d --name="influxdb" --restart on-failure -p 8086:8086 -v influx_data influxdb
