package rabbitmq.operator

import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class RabbitMQClient {
    companion object {
        private val LOG = LoggerFactory.getLogger(RabbitMQClient::class.java)
    }

    fun create(user: RabbitMQUserResource) {
        LOG.info("Create user: ${user.metadata.name}, admin: ${user.spec?.admin}")
    }

    fun update(user: RabbitMQUserResource) {
        LOG.info("Update user: ${user.metadata.name}, admin: ${user.spec?.admin}")
    }

    fun delete(user: RabbitMQUserResource) {
        LOG.info("Delete user: ${user.metadata.name}, admin: ${user.spec?.admin}")
    }
}