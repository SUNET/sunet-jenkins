import jenkins.model.Jenkins;

def locale = Jenkins.instance.getPlugin('locale')
locale.setSystemLocale("en_US")
locale.setIgnoreAcceptLanguage(true)
locale.save()
