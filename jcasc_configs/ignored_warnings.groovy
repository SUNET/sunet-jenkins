// vim: ts=4 sts=4 sw=4 et
import jenkins.model.Jenkins;

def ignoredWarnings = [
    'SECURITY-248', // Environment Injector Plugin before 1.91 stored sensitive build variables
]

// Get the plugin to configure
def uswc = Jenkins.instance.getExtensionList('jenkins.security.UpdateSiteWarningsConfiguration')[0]

// Add all the ignoredWarnings configured here
for (def ignored_warning in ignoredWarnings) {
    // @ is the way to access the private value directly in groovy
    // so we don't need to emulate the web interface and pass in json
    uswc.@ignoredWarnings.add(ignored_warning)
}

// And presist this to the xml
uswc.save()
