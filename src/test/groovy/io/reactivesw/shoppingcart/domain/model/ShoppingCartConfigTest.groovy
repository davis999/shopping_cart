package io.reactivesw.shoppingcart.domain.model

import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartConfigTest extends Specification {

    @Shared
    long configId = 1001L

    @Shared
    String configKey = "total_limit"

    @Shared
    String configValue = "42"

    @Shared
    Date createdTime = new Date()

    @Shared
    Date modifiedTime = new Date()

    ShoppingCartConfig scc1 = new ShoppingCartConfig(configId: configId, configKey: configKey, configValue: configValue, createdTime: createdTime, modifiedTime: modifiedTime)
    ShoppingCartConfig scc2 = new ShoppingCartConfig(configId: configId, configKey: configKey, configValue: configValue, createdTime: createdTime, modifiedTime: modifiedTime)
    ShoppingCartConfig scc3 = new ShoppingCartConfig(configId: configId, configKey: "other_config", configValue: configValue, createdTime: createdTime, modifiedTime: modifiedTime)

    def "hash code test"() {
        when:
        int hashValue1 = scc1.hashCode()
        int hashValue2 = scc2.hashCode()
        int hashValue3 = scc3.hashCode()
        int hashCompute = Objects.hash(configId, configKey, configValue, createdTime, modifiedTime)
        then:
        hashValue1 == hashCompute
        hashValue1 == hashValue2
        hashValue1 != hashValue3
    }

    def "equals test"() {
        expect:
        scc1.equals(scc2)
        !scc1.equals(scc3)
    }
}
