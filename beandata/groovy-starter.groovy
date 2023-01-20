
// load jars
if (1==0) {
    def f1='D:/maven-repository/repository/commons-beanutils/commons-beanutils/1.9.4/commons-beanutils-1.9.4.jar'
    def f2='D:/maven-repository/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar'
    def f3='D:/maven-repository/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar'
    def f4='D:/maven-repository/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar'
    def f5='D:/maven-repository/repository/net/datafaker/datafaker/1.3.0/datafaker-1.3.0.jar'
    def f6='D:/maven-repository/repository/com/github/mifmif/generex/1.0.2/generex-1.0.2.jar'
    def f7='D:/maven-repository/repository/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar'
    def f8='C:/Users/berni3/Documents/GitHub/prototyping/beandata/target/beandata-1.0-SNAPSHOT.jar'

    this.getClass().classLoader.rootLoader.addURL(new File(f1).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f2).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f3).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f4).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f5).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f6).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f7).toURL())
    this.getClass().classLoader.rootLoader.addURL(new File(f8).toURL())
} else {
    def urls = [
        'D:/maven-repository/repository/commons-beanutils/commons-beanutils/1.9.4/commons-beanutils-1.9.4.jar',
        'D:/maven-repository/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar',
        'D:/maven-repository/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar',
        'D:/maven-repository/repository/com/h2database/h2/1.4.200/h2-1.4.200.jar',
        'D:/maven-repository/repository/net/datafaker/datafaker/1.3.0/datafaker-1.3.0.jar',
        'D:/maven-repository/repository/com/github/mifmif/generex/1.0.2/generex-1.0.2.jar',
        'D:/maven-repository/repository/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar',
        'C:/Users/berni3/Documents/GitHub/prototyping/beandata/target/beandata-1.0-SNAPSHOT.jar'
    ]
    urls.each { theUrl -> 
        println "Importing ${theUrl}"
        this.getClass().classLoader.rootLoader.addURL(new File(theUrl).toURL())
    }

}