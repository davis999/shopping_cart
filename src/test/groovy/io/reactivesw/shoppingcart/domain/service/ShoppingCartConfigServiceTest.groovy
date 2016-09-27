package io.reactivesw.shoppingcart.domain.service

import io.reactivesw.shoppingcart.domain.model.ShoppingCartConfig
import io.reactivesw.shoppingcart.infrastructure.persistence.ShoppingCartConfigRepository
import spock.lang.Shared
import spock.lang.Specification

class ShoppingCartConfigServiceTest extends Specification {

    @Shared
    String key = "total_limit"

    @Shared
    String value = "42"

    ShoppingCartConfigService shoppingCartConfigService = new ShoppingCartConfigService()
    ShoppingCartConfigRepository shoppingCartConfigRepo = Mock()

    ShoppingCartConfig existedConfig = new ShoppingCartConfig(configId: 1001L, configKey: key, configValue: value, createdTime: new Date(), modifiedTime: new Date())

    def "get total limit"() {
        setup:
        shoppingCartConfigRepo.findOneByConfigKey("total_limit") >> existedConfig
        shoppingCartConfigService.shoppingCartConfigRepository = shoppingCartConfigRepo

        when:
        int totalLimit = shoppingCartConfigService.getTotalQuantityLimit()

        then: "save success"
        totalLimit == 42
    }

    def "get total limit null"() {
        setup:
        shoppingCartConfigRepo.findOneByConfigKey("total_limit") >> null
        shoppingCartConfigService.shoppingCartConfigRepository = shoppingCartConfigRepo

        when:
        int totalLimit = shoppingCartConfigService.getTotalQuantityLimit()

        then: "save success"
        totalLimit == 0
    }
}
