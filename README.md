# rabbitmq-operator

Java based k8s operator for demonstration purposes. 

It is no real rabbitmq operator, as it does not actually connect to RabbitMQ (yet) but is just an empty shell acting
as if it was a real operator and will just log.

I use it for the following showcasing purposes:
* write an k8s operator in java
* use Kotlin together with Micronaut
* access the k8s api with the fabric8 kubernetes client
* use jib and skaffold for speedy k8s deployments

# Prerequisites

To run this example you need a working k8s cluster, e.g. minikube to work locally.
Also you need helm v2 to use the provided helm chart for deployment, but there is also a simple manifest.yml to use instead.

## Installing

First install the CRD file, as the helm chart needs that to be deployed first:

```
kubectl apply -f crd.yaml
```

Build the application

```
./gradlew clean build
```

Next build the docker image

```
docker build . -t rabbitmq-operator:latest
```

Push the image to minikube

```
docker save rabbitmq-operator:latest | ( eval $(minikube docker-env) && docker load )
```

Now deploy the operator

```
helm upgrade --install rabbitmq-operator ./helm/rabbitmq-operator --wait
```

And access the log of the operator with this command:

```
kubectl logs -f -l "app.kubernetes.io/name=rabbitmq-operator"
```

## Using the Operator

Create a new custom resource by running this command

```
kubectl create -f cr.yaml
```

Now your log should show an creation event like this:

```
21:55:46.519 [OkHttp https://10.96.0.1/...] INFO  rabbitmq.operator.StartupListener - Received ADDED, Ressource: test 
21:55:46.522 [OkHttp https://10.96.0.1/...] INFO  rabbitmq.operator.RabbitMQClient - Creating user: test, admin: true
```

## Cleanup

To undeploy the helm chart, run this command:

```
helm delete rabbitmq-operator --purge
```