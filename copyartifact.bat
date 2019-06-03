copy C:\Jerry\java\hello.class C:\Jerry\java\hello.class%1%2
curl -uadmin:password -T C:\Jerry\java\hello.class%1%2 "http://localhost:8081/artifactory/example-repo-local/"