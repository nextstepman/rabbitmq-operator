package rabbitmq.operator

import io.fabric8.kubernetes.client.KubernetesClientException
import io.fabric8.kubernetes.client.Watcher
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import javax.inject.Inject
import javax.inject.Singleton
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess

@Singleton
class StartupListener : ApplicationEventListener<ServerStartupEvent> {
    companion object {
        private val LOG = LoggerFactory.getLogger(StartupListener::class.java)
    }

    @Inject
    lateinit var crdClient: RMQUserResourceMO

    @Inject
    lateinit var rmqClient: RabbitMQClient

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        LOG.info("Server Started")

        crdClient.watch(object : Watcher<RabbitMQUserResource> {

            override fun eventReceived(action: Watcher.Action, resource: RabbitMQUserResource) {
                LOG.info("Received ${action}, Ressource: ${resource.metadata.name} ")

                when(action) {
                    Watcher.Action.ADDED -> rmqClient.create(resource)
                    Watcher.Action.DELETED -> rmqClient.delete(resource)
                    Watcher.Action.MODIFIED -> rmqClient.update(resource)
                }
            }

            override fun onClose(cause: KubernetesClientException?) {
                if (cause != null) {
                    exitProcess(1)
                }
            }
        })

        LOG.info("Server Done")
    }
}