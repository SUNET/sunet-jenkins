import jenkins.model.Jenkins
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.SecretBytes
import org.jenkinsci.plugins.plaincredentials.FileCredentials
import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl

def store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()
def domain = Domain.global()

File file = new File("/run/secrets/GNUPG_KEYRING")
SecretBytes sb = SecretBytes.fromBytes(file.getBytes())
//FileItem fileItem = [ getName: { return "" } ] as FileItem
FileCredentials secretFile = new FileCredentialsImpl(
	CredentialsScope.GLOBAL,
	"GNUPG_KEYRING",
	"Our default GNUPG pubring.kbx, used for initial trust",
	null, // Don't use FileItem
	"pubring.kbx",
	sb)

store.addCredentials(domain, secretFile)

