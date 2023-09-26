package com.cslab.blovi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun test() = "시큐리티 ㅈㄴ어렵네 ㅅㅂ"
}