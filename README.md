# mqtt5-loadtest-jmsclient

## For running the program with big fat jar in Windows Command Prompt

### run-dbl.bat
```cmd
set JVM_OPTS=
set JVM_OPTS=%JVM_OPTS% -Dspring.profiles.active=case4
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.host=smf://10.8.0.223:55555
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.msg-vpn=ida-rs-csp
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.client-username=<to-be-provide>
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.client-password=<to-be-provide>
set JVM_OPTS=%JVM_OPTS% -Ddbl-odds.topic-zip=hk/d//prdt/wager/evt/01/upd/racing/20140120/s1/01/dbl/odds/full
set JVM_OPTS=%JVM_OPTS% -Ddbl-odds.f1=34
set JVM_OPTS=%JVM_OPTS% -Ddbl-odds.f2=34

java %JVM_OPTS% -jar mqtt5-loadtest-jmsclient-0.0.1-SNAPSHOT.jar
```

### run-winpla.bat
```cmd
set JVM_OPTS=
set JVM_OPTS=%JVM_OPTS% -Dspring.profiles.active=case5
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.host=smf://10.8.0.223:55555
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.msg-vpn=ida-rs-csp
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.client-username=<to-be-provide>
set JVM_OPTS=%JVM_OPTS% -Dsolace.jms.client-password=<to-be-provide>
set JVM_OPTS=%JVM_OPTS% -Dwin-odds.topic-zip=hk/d//prdt/wager/evt/01/upd/racing/20140120/s1/01/win/odds/full
set JVM_OPTS=%JVM_OPTS% -Dpla-odds.topic-zip=hk/d//prdt/wager/evt/01/upd/racing/20140120/s1/01/pla/odds/full
set JVM_OPTS=%JVM_OPTS% -Dodds.noOFHorse=14

java %JVM_OPTS% -jar mqtt5-loadtest-jmsclient-0.0.1-SNAPSHOT.jar
```
