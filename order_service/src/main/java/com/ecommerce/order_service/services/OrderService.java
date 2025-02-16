package com.ecommerce.order_service.services;

import com.ecommerce.order_service.dtos.OrderRequestDto;
import com.ecommerce.order_service.dtos.ProductIdAndPrice;
import com.ecommerce.order_service.dtos.ProductRequestDto;
import com.ecommerce.order_service.exceptions.CustomerNotPresentException;
import com.ecommerce.order_service.exceptions.ProductsNotFoundException;
import com.ecommerce.order_service.models.Order;
import com.ecommerce.order_service.dtos.UserData;
import com.ecommerce.order_service.models.OrderLine;
import com.ecommerce.order_service.repositories.OrderRepository;
import com.ecommerce.order_service.dtos.EmailContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private OrderRepository orderRepository;
    public Order createOrder(OrderRequestDto orderRequestDto) throws CustomerNotPresentException, ProductsNotFoundException, JsonProcessingException {
        Long customerId = orderRequestDto.getCustomerId();
        UserData userData = restTemplate.getForObject("http://localhost:8090/user/getUser/" + customerId,UserData.class);
        if(userData == null){
            throw new CustomerNotPresentException();
        }
        List<ProductRequestDto> productRequestDtoList = orderRequestDto.getProductRequestDtoList();
        List<Long> productIds = new ArrayList<>();
        for(ProductRequestDto productRequestDto : productRequestDtoList){
            productIds.add(productRequestDto.getProductId());
        }
        ProductIdAndPrice[] productIdAndPrices = restTemplate.postForObject("http://localhost:8090/product/getAllProductsByIds", productIds, ProductIdAndPrice[].class);
        if(productIdAndPrices == null){
            throw new ProductsNotFoundException();
        }
        double totalAmount = 0;
        Order order = new Order();
        List<OrderLine> orderLineList = new ArrayList<>();
        for(int i=0; i<productIdAndPrices.length; i++){
            OrderLine orderLine = new OrderLine();
            orderLine.setProductId(productIdAndPrices[i].getProductId());
            orderLine.setPrice(productIdAndPrices[i].getPrice());
            orderLine.setQuantity(productRequestDtoList.get(i).getQuantity());
            orderLine.setOrder(order);
            orderLineList.add(orderLine);
            totalAmount += productIdAndPrices[i].getPrice()*productRequestDtoList.get(i).getQuantity();
        }
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());
        order.setCustomerId(orderRequestDto.getCustomerId());
        order.setAmount(totalAmount);
        order.setOrderLineList(orderLineList);
        Order savedOrder = orderRepository.save(order);
        String subject = "Order Status";
        String body = "Order with id " + savedOrder.getId() + " is placed successfully";
        EmailContent emailContent = new EmailContent("guttikondaaravind39@gmail.com", userData.getEmail(), subject, body);
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(emailContent);
        kafkaTemplate.send("email-topic", valueAsString);
        return savedOrder;
    }
}
