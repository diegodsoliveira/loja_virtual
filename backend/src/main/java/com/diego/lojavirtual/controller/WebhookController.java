package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.integration.mautic.dto.WebhookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webhook")
public class WebhookController {

    @ResponseBody
    @PostMapping(value = "**/formSubmit")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookRequest webhookRequest) {
        // teste
        System.out.println("Received Webhook: " + webhookRequest);
        return ResponseEntity.ok().body("Sucesso");

    }
}
