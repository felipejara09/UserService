package com.pragma.powerup.domain.exception;

import static com.pragma.powerup.domain.util.ExceptionConstants.INVALID_DOCUMENT_MESSAGE;

public class InvalidDocumentException extends DomainException {
  public InvalidDocumentException() {
    super(INVALID_DOCUMENT_MESSAGE);
  }
}
