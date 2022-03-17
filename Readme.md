# Cloud SQL IAM Demo

This simple application demonstrates how Spring Data is able to connect to Cloud SQL via native IAM, via the Cloud SQL's Public IP.

The minimal configuration provided in [application.properties](src/main/resources/application.properties) tries to connect to the Cloud SQL proxy with the Default Application Credentials available on the runtime. These credentials could be user credentials which is set up via `gcloud auth application-default login` or instance credentials obtained from Workload Identity etc. This service account must have `Cloud SQL Client` role on the project.

The minimal configuration does not contain and MySQL username and password. In this case `root` user is used with empty password and if the Cloud SQL has such a user (`root/%` with no password), the connection succeeds, and your application can operate with these root credentials.

**IMPORTANT:** In real life, you may prefer to set up a password for the root user to prevent IAM users to use that account, create a less privileged user with or without a password and configure your application to use that new user. This user needs to allow connections from all IPs (`%`). Setting a password or not does not play an important role in this scenario since we trust that without passing the GCP IAM authentication, no applications can arrive to MySQL's authentication layer.

Compared to connections relying on private IP and MySQL authentication, the advantage of this approach are the following:

- You decouple your application's SQL connection from network and VPC configuration. This allows you to move your application across different projects or runtimes (e.g. Cloud Run) without needing to touch the SQL instance.
- Your connection to the SQL instance is by default encrypted
- Your application does not have to know the Public IP address of the SQL instance. The IP address is discovered automatically by the SDK.
- When your application runs on GCP, you do not need to manage any keys thanks to Workload Identity.

## More information

Please check out:

- Cloud SQL Auth proxy [documentation](https://cloud.google.com/sql/docs/mysql/sql-proxy)
- the Spring Cloud GCP [documentation](https://cloud.spring.io/spring-cloud-gcp/multi/multi__spring_jdbc.html#_spring_jdbc).