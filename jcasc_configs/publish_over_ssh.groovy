import jenkins.model.Jenkins;
import jenkins.plugins.publish_over_ssh.BapSshHostConfiguration

def publish_over_ssh = Jenkins.instance.getDescriptor("jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin")
// This is to make this re-entrant, so we don't create multiple entries
// if we somehow get run multiple times, like with a stored jenkins_home
publish_over_ssh.removeHostConfiguration("pypi.sunet.se")
def configuration = new BapSshHostConfiguration()
configuration.name = "pypi.sunet.se"
configuration.hostname = "pypi.sunet.se"
configuration.username = "pypi"
configuration.remoteRootDir = "/home/pypi/packages/"
// We need to override the implicit default of 0 to the actual default
configuration.port = configuration.DEFAULT_PORT
publish_over_ssh.addHostConfiguration(configuration)
publish_over_ssh.save()
