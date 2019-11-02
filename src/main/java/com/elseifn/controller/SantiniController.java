package com.elseifn.controller;

import com.elseifn.santini.mind.Santini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SantiniController {
  private final Santini santini;

  @Autowired
  public SantiniController(Santini santini) {
    this.santini = santini;
  }

  @RequestMapping(path = "/totalBalance", method = RequestMethod.GET)
  public ResponseEntity getTotalBalance() {
    return new ResponseEntity<>(santini.getTotalBalance(), HttpStatus.OK);
  }

  @RequestMapping(path = "/shutdown", method = RequestMethod.GET)
  public void shutdown() {
    System.exit(-1);
  }

  @RequestMapping(path = "/state", method = RequestMethod.GET)
  public ResponseEntity getState() {
    String response = "Have you ever seen anything so full of splendor?";
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
