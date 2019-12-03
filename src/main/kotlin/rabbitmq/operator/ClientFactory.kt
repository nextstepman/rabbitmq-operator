package rabbitmq.operator

import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.internal.KubernetesDeserializer
import io.micronaut.context.annotation.Factory
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Singleton

@Factory
internal class ClientFactory {
    companion object {
        private val LOG = LoggerFactory.getLogger(ClientFactory::class.java)
    }

    @Singleton
    fun kubernetesClient(): KubernetesClient {
        LOG.info("Creating Client")

        val namespace = String(Files.readAllBytes(
                Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/namespace")))

        return DefaultKubernetesClient().inNamespace(namespace)
    }

    @Singleton
    internal fun createRMQUserResourceMO(client: KubernetesClient): RMQUserResourceMO {
        KubernetesDeserializer.registerCustomKind("senacor.com/v1", "RabbitMQUser",
                RabbitMQUserResource::class.java)

        val crd = client.customResourceDefinitions().list().items.stream()
                .filter { p -> p.spec.group == "senacor.com" }
                .filter { p -> p.spec.names.kind == "RabbitMQUser" }
                .findFirst()

        if (crd.isEmpty) {
            LOG.error("!!! CRD not found, exiting. Make sure to create it")
            System.exit(1);
        }

        return client.customResources(crd.get(), RabbitMQUserResource::class.java,
                RabbitMQUserResourceList::class.java, RabbitMQUserResourceDonable::class.java)
    }
}