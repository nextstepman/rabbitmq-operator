package rabbitmq.operator

import io.fabric8.kubernetes.api.builder.Function
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.client.CustomResourceDoneable
import io.fabric8.kubernetes.client.CustomResourceList
import io.fabric8.kubernetes.client.dsl.MixedOperation
import io.fabric8.kubernetes.client.dsl.Resource
import io.micronaut.core.annotation.Introspected

@Introspected
class RabbitMQUserResource : CustomResource() {
    var spec: RabbitMQUserResourceSpec? = null
}

@Introspected
class RabbitMQUserResourceSpec {
    var admin: Boolean? = null
}

@Introspected
class RabbitMQUserResourceList : CustomResourceList<RabbitMQUserResource>()

class RabbitMQUserResourceDonable(resource: RabbitMQUserResource, function: Function<RabbitMQUserResource, RabbitMQUserResource>) : CustomResourceDoneable<RabbitMQUserResource>(resource, function)

typealias RMQUserResourceMO = MixedOperation<RabbitMQUserResource, RabbitMQUserResourceList, RabbitMQUserResourceDonable, Resource<RabbitMQUserResource, RabbitMQUserResourceDonable>>