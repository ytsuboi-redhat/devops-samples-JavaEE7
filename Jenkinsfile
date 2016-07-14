stage 'build'
node {
    git url: 'https://github.com/ytsuboi-redhat/devops-samples-JavaEE7.git'
    withEnv(["PATH+MAVEN=${tool 'maven 3.3.9'}/bin"]) {
        sh "mvn clean install -f dev -P development -P sonar-coverage"
        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        sh "mvn sonar:sonar -f dev"
    }
}

stage 'setup-staging'
node {
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/dbservers.yml'
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/eapservers.yml'
}

stage 'serverspec-staging'
node {
    sh "cd " + pwd() + "/serverspec; sudo ~/.gem/ruby/bin/rake"
}

stage 'integration-test'
node {
    withEnv(["PATH+MAVEN=${tool 'maven 3.3.9'}/bin"]) {
        sh "mvn integration-test -f dev -P arq-wildfly-remote"
    }
}

stage 'deploy-staging'
node {
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/deploy.yml', extras: '--extra-vars "jenkins_build_ear=' + pwd() + '/dev/ear/target//ear-0.1-SNAPSHOT.ear"'
}

stage 'setup-production'
node {
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/dbservers.yml'
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/eapservers.yml'
}

stage 'serverspec-production'
node {
    sh "cd " + pwd() + "/serverspec; sudo ~/.gem/ruby/bin/rake"
}

stage 'deploy-production'
node {
    ansiblePlaybook inventory: 'env/hosts', playbook: 'env/deploy.yml', extras: '--extra-vars "jenkins_build_ear=' + pwd() + '/dev/ear/target//ear-0.1-SNAPSHOT.ear"'
}

stage 'performance-test'
node {
    sh "LANG=en_US.UTF-8 /opt/jmeter/2.13/bin/jmeter -n -Jjmeter.save.saveservice.output_format=xml -t /tmp/performance.jmx -l /tmp/result.jtl"
}
