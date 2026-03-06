package com.order.order.service;

import com.inventory.inventory.dto.InventoryDTO;
import com.order.order.common.ErrorOrderResponse;
import com.order.order.common.OrderResponse;
import com.order.order.common.SuccessOrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.model.Orders;
import com.order.order.repo.OrderRepo;
import com.product.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;


    public OrderService(@Qualifier("inventoryWebClient") WebClient inventoryWebClient,
                       @Qualifier("productWebClient") WebClient productWebClient,
                       OrderRepo orderRepo,
                       ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders>orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDTO OrderDTO) {
        Integer itemId = OrderDTO.getItemId();

        logger.info("========== Processing order for itemId: {} ==========", itemId);

        try {
            logger.info("Step 1: Calling inventory service at: http://inventory/api/v1/item/{}", itemId);

            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(), clientResponse -> clientResponse.createException())
                    .bodyToMono(InventoryDTO.class)
                    .doOnError(e -> logger.error("Error calling inventory service: {}", e.getMessage(), e))
                    .block();

            logger.info("Step 2: Inventory response received: {}", inventoryResponse);

            if (inventoryResponse == null) {
                logger.error("ERROR: Inventory response is null for itemId: {}", itemId);
                return new ErrorOrderResponse("Item not found");
            }

            Integer productId = inventoryResponse.getProductId();
            logger.info("Step 3: Got productId: {} for itemId: {}", productId, itemId);

            logger.info("Step 4: Calling product service at: http://product/api/v1/product/{}", productId);

            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(), clientResponse -> clientResponse.createException())
                    .bodyToMono(ProductDTO.class)
                    .doOnError(e -> logger.error("Error calling product service: {}", e.getMessage(), e))
                    .block();

            logger.info("Step 5: Product response received: {}", productResponse);

            if (productResponse == null) {
                logger.error("ERROR: Product response is null for productId: {}", productId);
                return new ErrorOrderResponse("Product not found");
            }

            logger.info("Step 6: Validating order - Quantity: {}, ForSale: {}",
                inventoryResponse.getQuantity(), productResponse.getForSale());

            if (inventoryResponse.getQuantity() > 0) {
                if (productResponse.getForSale() == 1) {
                    orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
                    logger.info("SUCCESS: Order saved successfully for itemId: {}", itemId);
                    return new SuccessOrderResponse(OrderDTO);
                }
                else {
                    logger.warn("Product is not for sale (forSale={})", productResponse.getForSale());
                    return new ErrorOrderResponse("This item is not for sale");
                }
            }
            else {
                logger.warn("Item not available - quantity is {} or less", inventoryResponse.getQuantity());
                return new ErrorOrderResponse("Item not available, please try later");
            }

        }
        catch (WebClientResponseException e) {
            logger.error("WebClientResponseException: Status={}, StatusCode={}, Message={}",
                e.getStatusCode(), e.getRawStatusCode(), e.getMessage(), e);
            return new ErrorOrderResponse("Item not found - Service communication error: " + e.getMessage());
        }
        catch (Exception e) {
            logger.error("FATAL Exception occurred: Type={}, Message={}", e.getClass().getName(), e.getMessage(), e);
            e.printStackTrace();
            return new ErrorOrderResponse("Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, Orders.class));
        return OrderDTO;
    }

    public String deleteOrder(Integer orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

    public OrderDTO getOrderById(Integer orderId) {
        Orders order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}
