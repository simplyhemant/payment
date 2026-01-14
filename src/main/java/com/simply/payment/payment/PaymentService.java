package com.simply.payment.payment;

import com.simply.payment.notification.NotificationProducer;
import com.simply.payment.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {

        //Convert request to entity
        Payment payment = paymentMapper.toPayment(request);

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Create notification request
        PaymentNotificationRequest notification =
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                );

        // Send notification
        notificationProducer.sendNotification(notification);

        // Return payment id
        return savedPayment.getId();
    }

}
