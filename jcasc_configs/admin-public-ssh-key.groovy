// vim: ts=4 sts=4 sw=4 et

try {
    def user = hudson.model.User.get('admin')
    def f = new File("/run/secrets/ADMIN_SSH_KEY")
    def pubKey = new org.jenkinsci.main.modules.cli.auth.ssh.UserPropertyImpl(f.text)

    user.addProperty(pubKey)
    user.save()
    println("ADMIN_SSH_KEY configured")
} catch(e) {
    println("Failed to add ssh key to admin:")
    println(e)
}
