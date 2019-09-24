### Setup
Clone this repo.
Create a symlink from /var/jenkins_home to /var/lib/docker/volumes/jenkins_compose_jenkins_home/_data
(This togeather with JAVA_OPTS=-Djava.io.tmpdir=/var/jenkins_home/tmp is
needed to get docker-custom-build-environment to work in docker in docker setup)
```bash
ln -s /var/lib/docker/volumes/jenkins_compose_jenkins_home/_data /var/jenkins_home
```
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
