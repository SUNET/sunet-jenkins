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
Run start.sh.
```bash
./start.sh
```

#### Test build your own repos
Modify the main loop of github_docker_repos.groovy to use another users repos as follows.
```diff
def orgs = ['SUNET']
+def users = ['YOUR_GITHUB_USERNAME']
 def url = "https://api.github.com/"

-orgs.each {
-    def next_path = "/orgs/${it}/repos"
+//orgs.each {
+users.each {
+    //def next_path = "/orgs/${it}/repos"
+    def next_path = "/users/${it}/repos"
     def next_query = null
     def api = new HTTPBuilder(url)
```
