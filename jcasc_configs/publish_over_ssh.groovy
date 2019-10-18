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
// If we have a /run/secrets/PUBLISH_OVER_SSH_KEY configure it as key
try {
	def f = new File("/run/secrets/PUBLISH_OVER_SSH_KEY")
	configuration.setKey(f.text)
	//configuration.setKeyPath("/run/secrets/PUBLISH_OVER_SSH_KEY")
	/*
	ERROR: Exception when publishing, exception message [Failed to read file - filename [/run/secrets/PUBLISH_OVER_SSH_KEY] (relative to JENKINS_HOME if not absolute). Message: [java.lang.SecurityException: agent may not read /run/secrets/PUBLISH_OVER_SSH_KEY
	See https://jenkins.io/redirect/security-144 for more details]]

	Either we open that in agent access, or we just set it in jenkins.
	*/
	configuration.setOverrideKey(true)
	println("PUBLISH_OVER_SSH_KEY configured")
} catch(e) {
	println("Failed to run setKey(/run/secrets/PUBLISH_OVER_SSH_KEY)")
	println(e)
}
publish_over_ssh.addHostConfiguration(configuration)
publish_over_ssh.save()
