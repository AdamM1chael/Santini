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
  @Autowired
  private Santini dolores;

  @RequestMapping(path = "/totalBalance", method = RequestMethod.GET)
  public ResponseEntity getTotalBalance() {

    return new ResponseEntity(dolores.getTotalBalance(), HttpStatus.OK);
  }
}
