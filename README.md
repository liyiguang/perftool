perftool
========

A performance test tool and framework,Can use to do both client and server performance test. Currently test cases include Memcached,Http clients.It is easy to extend more test case


### usage
```bash
sbt clean
sbt xitrum-package
cd target/xitrum
./bin/perf -c 100 -n 1000  perf.mc.WhalinTestGet

Test warm up finished !
---------------------- report start -----------------------------
requests:100000
Send Request: 100000
Successful Request: 100000
Response rate: 75357 rsp/s 
Response time [ms]: avg 0 min 0 max 54
Response time [ms]: p25 1 p50 1 p75 1
Response time [ms]: p80 1 p90 2 p95 2
Response time [ms]: p99 5 p99.9 19 p100 54
Errors: total 0
---------------------- report end -------------------------------

./bin/perf -c 100 -n 10  -u http://www.google.com

---------------------- report start -----------------------------
requests:998
Send Request: 1000
Successful Request: 998
Response rate: 156 rsp/s 
Response time [ms]: avg 544 min 195 max 2022
Response time [ms]: p25 404 p50 512 p75 601
Response time [ms]: p80 653 p90 751 p95 868
Response time [ms]: p99 1533 p99.9 1745 p100 2022
Errors: total 2
---------------------- report end -------------------------------

```

### todo
1. Add redis client for redis client and server test

### extend
write a subclass or com.yiguang.perf.Test


 


