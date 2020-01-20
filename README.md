### Setup
Clone this repo.
Create a symlink to or clone the repo [bootstrap-docker-builds](https://github.com/SUNET/bootstrap-docker-builds) in /path/to/sunet-jenkins-developer/bootstrap-docker-builds.
```bash
git clone git@github.com:SUNET/bootstrap-docker-builds.git /path/to/sunet-jenkins-developer/
```
Clone the docker-jenkins-job repo and build its containers with the docker-wrapper
to turn docker push into a nop, so you won't fail those jobs or affect the real registry:
```bash
git clone git@github.com:SUNET/docker-jenkins-job.git /path/to/docker-jenkins-job
make -C /path/to/docker-jenkins-job -j all_extra_job_docker_wrapper
```
Run start.sh.
```bash
./start.sh
```

### How to run (as) in prod?
* Clone this repo
* Create a github Oauth App at https://github.com/settings/developers and put GITHUB_CLIENT_ID GITHUB_CLIENT_SECRET in .env
* Create and put a SLACK_TOKEN in .env
* Create and put a GITHUB_TOKEN in .env
* Write a list of admins github usernames as GITHUB_ADMIN_USERNAMES in .env
* Get the certificates for the docker host to run agents on and point out those files with DOCKER_SERVER_CA_CERTIFICATE DOCKER_CLIENT_CERTIFICATE DOCKER_CLIENT_KEY in .env
* Configure the DOCKER_URI pointing to the right host in .env
* Get the certificates for the service and point out that bundle with DEHYDRATED_BUNDLE in .env
* Point out initial trust key chain with GNUPG_KEYRING in .env
* And run it as:
```bash
./bin/docker-compose -f jenkins_compose/compose.yml -f jenkins_compose/prod.yml
```

#### Test build your own repos
Create a folder, and in that folder create a copy of the bootstrap-docker-builds
job, and edit its enviorment.
(New item, Copy from, Inject environment variables to the build process, Properties Content)
```
ORGS=["YOUR_GITHUB_USERNAME"]
REPOS=["docker-jenkins-job", "docker-jenkins"]
```
You can omit REPOS, if you would like it to generate jobs for all your repos.
Both variables contain json as a string.
Another usecase is to not enter another ORGS, and just filter out the REPOS
you're interested in and let your local jenkins build those.
One can also put ORGS and/or REPOS in your local .env file, and those
will be picked up and applied when running in dev mode.

And to test your own branch of bootstrap-docker-builds, just create a folder,
create a copy of the bootstrap-docker-builds job there and edit its
branch-field to point to your own branch. This way, its easy to compare how
your branch works against the current master.

Another trick which is quite handy when running jenkins locally for testing
is that in dev mode, the jenkins-internal ssh server is enabled and by defaut
the public key found in X is provisioned on the admin user. The public key
provisioned can be controlled by pointing the env-var ADMIN_SSH_KEY to
another file.
That way, you can access the cli over ssh and trigger builds and look at
console logs like:
```
ssh -l admin -p 8022 172.16.12.2 console -f bootstrap-docker-builds lastBuild

ssh -l admin -p 8022 172.16.12.2 build -sv scripted-pipeline/bootstrap-docker-builds && \
ssh -l admin -p 8022 172.16.12.2 build -sv scripted-pipeline/simple-fail-page
```
Thats a simple way to ad-hoc chain jobs and shorten your test-dev-cycle.


#### My github requests are failing!
java.io.IOException: Server returned HTTP response code: 403 for URL: https://api.github.com/

This is because github is rate limiting your api calls. Go to
https://github.com/settings/tokens and get yourself a token and put it
as GITHUB_TOKEN in .env, and you don't run into the request api request
limit as quickly...
