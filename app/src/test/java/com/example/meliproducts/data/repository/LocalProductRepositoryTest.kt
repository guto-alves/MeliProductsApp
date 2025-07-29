package com.example.meliproducts.data.repository

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.meliproducts.data.util.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class LocalProductRepositoryTest {
    @Before
    fun setup() {
        mockkStatic("android.util.Log")
        every { Log.d(any<String>(), any<String>()) } returns 0
        every { Log.e(any<String>(), any<String>(), any<Throwable>()) } returns 0
        every { Log.w(any<String>(), any<String>(), any<Throwable>()) } returns 0
        every { Log.w(any<String>(), any<String>()) } returns 0
    }

    @Test
    fun searchProducts_ValidQuery_ReturnsSuccess() = runBlocking {
        val context = mockk<Context>(relaxed = true)
        val assetManager = mockk<AssetManager>(relaxed = true)

        val inputStream = fakeJson.byteInputStream()
        every { context.assets } returns assetManager
        every { assetManager.open(any()) } returns inputStream

        val repository = LocalProductRepository(context)
        val result = repository.searchProducts("iphone")

        assertTrue(result is Result.Success)

        val products = (result as Result.Success).data
        assertEquals(1, products.size)
        assertEquals("Apple iPhone 13 (128 Gb) - Azul - Distribuidor Autorizado", products[0].title)
    }

    @Test
    fun searchProducts_InvalidQuery_ReturnsError() = runBlocking {
        val context = mockk<Context>(relaxed = true)
        val assetManager = mockk<AssetManager>(relaxed = true)

        every { context.assets } returns assetManager
        every { assetManager.open(any()) } throws FileNotFoundException()

        val repository = LocalProductRepository(context)
        val result = repository.searchProducts("invalid")

        assertTrue(result is Result.Error)
    }
}


private val fakeJson = """
{
"site_id": "MLA",
  "country_default_time_zone": "GMT-03:00",
  "query": "iphone",
  "paging": {
    "total": 11421,
    "primary_results": 1000,
    "offset": 0,
    "limit": 50
  },
  "results": [
    {
      "id": "MLA1116621831",
      "title": "Apple iPhone 13 (128 Gb) - Azul - Distribuidor Autorizado",
      "condition": "new",
      "thumbnail_id": "619667-MLA47781882790_102021",
      "catalog_product_id": "MLA1018500846",
      "listing_type_id": "gold_pro",
      "sanitized_title": "",
      "permalink": "https://www.mercadolibre.com.ar/apple-iphone-13-128-gb-azul-distribuidor-autorizado/p/MLA1018500846#wid=MLA1116621831&sid=unknown",
      "buying_mode": "buy_it_now",
      "site_id": "MLA",
      "category_id": "MLA1055",
      "domain_id": "MLA-CELLPHONES",
      "thumbnail": "http://http2.mlstatic.com/D_619667-MLA47781882790_102021-I.jpg",
      "currency_id": "ARS",
      "order_backend": 1,
      "price": 1874999,
      "original_price": 2083333,
      "sale_price": {
        "price_id": "",
        "amount": 1874999,
        "conditions": {
          "eligible": true,
          "context_restrictions": ["channel_marketplace"],
          "start_time": "2025-01-07T03:00:00Z",
          "end_time": "2025-04-01T03:00:00Z"
        },
        "currency_id": "ARS",
        "exchange_rate": null,
        "payment_method_prices": [],
        "payment_method_type": "TMP",
        "regular_amount": 2083333,
        "type": "promotion",
        "metadata": {
          "promotion_type": "campaign",
          "campaign_id": "P-MLA14541464",
          "promotion_id": "OFFER-MLA1116621831-10269792999"
        }
      },
      "available_quantity": 1,
      "official_store_id": 2549,
      "official_store_name": "Apple",
      "use_thumbnail_id": true,
      "accepts_mercadopago": true,
      "shipping": {
        "store_pick_up": false,
        "free_shipping": true,
        "logistic_type": "cross_docking",
        "mode": "me2",
        "tags": ["self_service_in", "mandatory_free_shipping"],
        "benefits": null,
        "promise": null,
        "shipping_score": -1
      },
      "stop_time": "2041-12-15T04:00:00.000Z",
      "seller": { "id": 169311474, "nickname": "IPOINT" },
      "address": {
        "state_id": "AR-B",
        "state_name": "Buenos Aires",
        "city_id": null,
        "city_name": "Benavidez"
      },
      "attributes": [
        {
          "id": "BRAND",
          "name": "Marca",
          "value_id": "9344",
          "value_name": "Apple",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            { "id": "9344", "name": "Apple", "struct": null, "source": 1 }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "COLOR",
          "name": "Color",
          "value_id": "52028",
          "value_name": "Azul",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            { "id": "52028", "name": "Azul", "struct": null, "source": 1 }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "DETAILED_MODEL",
          "name": "Modelo detallado",
          "value_id": "22787241",
          "value_name": "MLPK3BR/A",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            {
              "id": "22787241",
              "name": "MLPK3BR/A",
              "struct": null,
              "source": 4709228965570453
            }
          ],
          "source": 4709228965570453,
          "value_type": "string"
        },
        {
          "id": "GPU_MODEL",
          "name": "Modelo de GPU",
          "value_id": "7741027",
          "value_name": "Apple GPU",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            {
              "id": "7741027",
              "name": "Apple GPU",
              "struct": null,
              "source": 1
            }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "GTIN",
          "name": "Código universal de producto",
          "value_id": null,
          "value_name": "194252708231",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            { "id": null, "name": "194252708231", "struct": null, "source": 1 }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "ITEM_CONDITION",
          "name": "Condición del ítem",
          "value_id": "2230284",
          "value_name": "Nuevo",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            { "id": "2230284", "name": "Nuevo", "struct": null, "source": 1 }
          ],
          "source": 1,
          "value_type": "list"
        },
        {
          "id": "LINE",
          "name": "Línea",
          "value_id": "13834194",
          "value_name": "iPhone 13",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            {
              "id": "13834194",
              "name": "iPhone 13",
              "struct": null,
              "source": 2579503448603610
            }
          ],
          "source": 2579503448603610,
          "value_type": "string"
        },
        {
          "id": "MAIN_COLOR",
          "name": "Color principal",
          "value_id": "2450293",
          "value_name": "Azul",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            { "id": "2450293", "name": "Azul", "struct": null, "source": 1 }
          ],
          "source": 1,
          "value_type": "list"
        },
        {
          "id": "MODEL",
          "name": "Modelo",
          "value_id": "11159139",
          "value_name": "iPhone 13",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            {
              "id": "11159139",
              "name": "iPhone 13",
              "struct": null,
              "source": 1
            }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "PROCESSOR_MODEL",
          "name": "Modelo del procesador",
          "value_id": "11151775",
          "value_name": "Apple A15 Bionic",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": null,
          "values": [
            {
              "id": "11151775",
              "name": "Apple A15 Bionic",
              "struct": null,
              "source": 1
            }
          ],
          "source": 1,
          "value_type": "string"
        },
        {
          "id": "WEIGHT",
          "name": "Peso",
          "value_id": "462013",
          "value_name": "173 g",
          "attribute_group_id": "OTHERS",
          "attribute_group_name": "Otros",
          "value_struct": { "number": 173, "unit": "g" },
          "values": [
            {
              "id": "462013",
              "name": "173 g",
              "struct": { "number": 173, "unit": "g" },
              "source": 1
            }
          ],
          "source": 1,
          "value_type": "number_unit"
        }
      ],
      "installments": {
        "quantity": 9,
        "amount": 208333.22,
        "rate": 0,
        "currency_id": "ARS",
        "metadata": {
          "meliplus_installments": false,
          "additional_bank_interest": false
        }
      },
      "winner_item_id": null,
      "catalog_listing": true,
      "discounts": null,
      "promotion_decorations": null,
      "promotions": null,
      "inventory_id": null,
      "installments_motors": null,
      "result_type": "item"
    }
   ]
}
""".trimIndent()