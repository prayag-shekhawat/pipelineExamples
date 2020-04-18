def createDeploymentJob(jobName, repoUrl) {
	pipelineJob(jobName) {
		definition {
			cpsScm {
				scm {
					git {
						remote {
							url(repoUrl)
						}
						branches('master')
						extensions {
							cleanBeforeCheckout()
						}
					}
				}
				scriptPath("Jenkinsfile")
			}
		}
	}
}

def createTestJob(jobName, repoUrl) {
	multibranchPipelineJob(jobName) {
		branchSources {
			git credentialsId: 'fab1633f-9ec8-4e41-9e04-43609d9a53de' {
				remote(repoUrl)
				includes('*')
			}
		}
		triggers {
			cron("H/5 * * * *")
		}
	}
}

def buildPipelineJobs() {
	def repo = "git@github.com:prayag-shekhawat/"
	def repoUrl = repo + jobName + ".git"
	def deployName = jobName + "_deploy"
	def testName = jobName + "_test"
	
	createDeploymentJob(deployName, repoUrl)
	createTestJob(testName, repoUrl)
}

buildPipelineJobs()
