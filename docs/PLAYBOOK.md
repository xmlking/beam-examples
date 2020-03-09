DIY Playbook
============

Do-it-yourself step-by-step instructions to create this project structure from scratch.


### Prerequisites  
> you need following tools. versions listed here are minimal versions tested.

| Software                      | Version         | Optional         |  
|-------------------------------|-----------------|------------------| 
| Java                          | 1.8.242         |                  | 
| Kotlin                        | 1.13.70         |                  | 
| Apache Beam                   | 2.19.0          |                  |
| Gradle                        | 6.2.2           |                  |
| IntelliJ                      |                 | 2020.1           |
| Docker for Mac                | latest          |                  |
| SDKMan                        | latest          |                  |


### Install Prerequisites
```bash
# install or Update Node with brew or NVM
sdk install java 11.0.6.hs-adpt
sdk install java 8.0.242.hs-adpt
sdk install java 120.0.0.r11-grl 
# sdk default java 20.0.0.r11-grl
sdk default java 8.0.242.hs-adpt
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

### IntelliJ IDEA 
Be sure to enable delegate IDE build/run actions to Gradle so that Intellij does not use its internal build mechanism to compile source code. 

```
Settings -> Build, Execution, Deployment
  -> Build Tools -> Gradle -> Runner
  -> Delegate IDE build/run actions to gradle.
```

Point to local Gradle instead of gradle in wrapper
```
Settings -> Build, Execution, Deployment
    -> Build Tools -> Gradle
    -> Gradle -> set 'Use Gradle From' to 'Specified Loction' to local gradle for eg '/Users/{user-name}/.sdkman/candidates/gradle/6.0.1' 
```

Install **SonarLint** Plugin for IntelliJ

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

 ## gCloud
 
 ```bash
 export PROJECT_ID=micro-starter-kit
 export GCS_BUCKET=micro-starter-kit
 export GOOGLE_APPLICATION_CREDENTIALS="/Users/sumo/Developer/Apps/micro-starter-kit.json"
```
 
 ### Setup Network 
 ```bash
 export PROJECT_ID=micro-starter-kit
 export COMPUTE_REGION=us-west1
 export COMPUTE_ZONE=us-west1
 
 export MANAGED_ZONE_NAME=micro-zone-name
 export NETWORK_NAME=micro-network
 export SUBNET_NAME=micro-subnet
 export SUBNET_RANGE=10.0.0.0/20
 export SUBNET_PODS_NAME=micro-subnet-pods
 export SUBNET_PODS_RANGE=10.11.0.0/16
 export SUBNET_SERVICES_NAME=micro-subnet-services
 export SUBNET_SERVICES_RANGE=10.12.0.0/18
 
 export FIREWALL_RULE_NAME=dataflow-allow-internal
 
 # Create VPC network
 gcloud compute networks create ${NETWORK_NAME} \
     --project ${PROJECT_ID} \
     --region ${COMPUTE_REGION} \
     --subnet-mode custom
 
 
 #  Create a subnet
 gcloud compute networks subnets create ${SUBNET_NAME} \
   --project ${PROJECT_ID}  \
   --network ${NETWORK_NAME}  \
   --region ${COMPUTE_REGION}    \
   --range ${SUBNET_RANGE}    \
   --secondary-range ${SUBNET_PODS_NAME}=${SUBNET_PODS_RANGE},${SUBNET_SERVICES_NAME}=${SUBNET_SERVICES_RANGE} \
   --enable-private-ip-google-access
 
 # Verify
 gcloud compute networks subnets list --network ${NETWORK_NAME}
 gcloud compute networks subnets describe ${SUBNET_NAME}
 
 # crerate dataflow-allow-internal firewall rule
 gcloud compute firewall-rules create ${FIREWALL_RULE_NAME} \
     --network ${NETWORK_NAME} \
     --action allow \
     --direction ingress \
     --target-tags dataflow \
     --source-tags dataflow \
     --priority 0 \
     --rules tcp:12345-12346
 ```