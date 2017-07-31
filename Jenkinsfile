@Library("21re") _
gen.init()

publish = false
if(env.BRANCH_NAME == 'master') {
    publish = true
}

node {
    checkout scm

    sbtBuild([cmds: "clean compile test"])

    if(publish) {
      sbtBuild([cmds: "+publish"])
    }
}
