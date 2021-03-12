package com.webservice.bookstore.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class OrdersController {
}
