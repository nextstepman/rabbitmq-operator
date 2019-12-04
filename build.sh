kubectl apply -f crd.yaml

docker build . -t rabbitmq-operator:latest

docker save rabbitmq-operator:latest | ( eval $(minikube docker-env) && docker load )

helm upgrade --install rabbitmq-operator helm/rabbitmq-operator --recreate-pods --wait

kubectl logs -f -l "app.kubernetes.io/instance=rabbitmq-operator"