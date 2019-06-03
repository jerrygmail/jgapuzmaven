copy target\sandbox-1.0-SNAPSHOT.war target\sandbox-1.0-SNAPSHOT.war%1%2
curl -uadmin:password -T target\sandbox-1.0-SNAPSHOT.war%1%2 "http://localhost:8081/artifactory/example-repo-local/"