package com.elseifn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SantiniController {

  @RequestMapping(path = "/totalBalance", method = RequestMethod.GET)
  public ResponseEntity getTotalBalance() {
    return new ResponseEntity("HI", HttpStatus.OK);
  }
}
