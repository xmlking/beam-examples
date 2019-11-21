DIY Playbook
============

Do-it-yourself step-by-step instructions to create this project structure from scratch.


### Prerequisites  
> you need following tools. versions listed here are minimal versions tested.

| Software                      | Version         | Optional         |  
|-------------------------------|-----------------|------------------| 
| Java                          | 13.0.1          | 13.0.1.j9-adpt   | 
| Kotlin                        | 1.13.60         |                  | 
| Apache Beam                   | 2.0.4.RELEASE   |                  |
| Gradle                        | 6.0.1           |                  |
| IntelliJ                      |                 | 2019.3           |
| Docker for Mac                | latest          |                  |
| SDKMan                        | latest          |                  |



### Install Prerequisites
```bash
# install or Update Node with brew or NVM
sdk install java 13.0.1.j9-adpt
sdk install java 19.2.1-grl
sdk default java 19.2.1-grl
sdk install gradle
# to remove old version e.g., gradle 4.10:
sdk remove gradle 4.10
sdk install kotlin 
# Optional
sdk install maven
sdk install 
#sdkman self upgrade
sdk selfupdate
```

### Install Kubernetes (optional)
follow instructions [here](https://gist.github.com/xmlking/62ab53753c0f0f5247d0e174b31dab21) to install kubernetes toolchain:
1. Docker for Mac (edge version)
2. Kustomize (optional)
3. kubectx (optional)


### Scaffold Project
> steps below are for setting up a new project from the scratch.


#### Create Workspace
```bash
mkdir dataflow && cd dataflow
gradle init --type kotlin-application --dsl kotlin
```
 
#### Generate Artifacts
TODO
```bash
cd apps/greeting-api
mn create-controller micro.apps.greeting.controllers.greeting
mn create-bean  micro.apps.greeting.services.greetingService
mn create-client greetingClient
```

### Run

#### Docker
> start mongodb, kafka
```bash
# start local mongodb
docker-compose up -V mongodb
# stop local mongodb before restart again
docker-compose down -v
# start local kafka
docker-compose up broker
```

### Gradle Commands
```bash
# upgrade project gradle version
gradle wrapper --gradle-version 6.0.6 --distribution-type all
# gradle daemon status 
gradle --status
gradle --stop
# show dependencies
gradle classifier:dependencies
gradle classifier:dependencyInsight --dependency spring-messaging
# refresh dependencies
gradle build -x test --refresh-dependencies 

# display version 
gradle :versionDisplay
```

 