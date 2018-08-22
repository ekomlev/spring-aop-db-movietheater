CREATE TABLE t_auditorium (
    id            BIGINT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_auditorium PRIMARY KEY,
    name          VARCHAR(30) NOT NULL,
    numberOfSeats BIGINT,
    vipSeats      VARCHAR(80)
);


CREATE TABLE t_user (
      id         BIGINT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_user PRIMARY KEY,
      firstName  VARCHAR(100),
      lastName   VARCHAR(60),
      email      VARCHAR(60),
      birthday   VARCHAR(30)
);

CREATE TABLE t_event (
      id            BIGINT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_event PRIMARY KEY,
      name          VARCHAR(100) NOT NULL,
      airDate       VARCHAR(30),
      basePrice     DECIMAL(8,2),
      rating        VARCHAR(10),
      auditorium_id BIGINT CONSTRAINT fk_event_auditorium
      				REFERENCES t_auditorium ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE t_counter (
    id                            BIGINT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_counter PRIMARY KEY,
    eventByNameCallCounter        BIGINT,
    eventTicketPriceCallCounter   BIGINT,
    eventTicketBookingCallCounter BIGINT,
    ticketDiscountCallCounter     BIGINT,
    event_id                      BIGINT CONSTRAINT fk_counter_event
                                  REFERENCES t_event ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE t_ticket (
      id        BIGINT GENERATED ALWAYS AS IDENTITY CONSTRAINT pk_ticket PRIMARY KEY,
      dateTime  VARCHAR(30),
      seat      BIGINT,
      price     DECIMAL(8,2),
      user_id   BIGINT CONSTRAINT fk_ticket_user
                REFERENCES t_user ON UPDATE RESTRICT ON DELETE CASCADE,
      event_id  BIGINT CONSTRAINT fk_ticket_event
                REFERENCES t_event ON UPDATE RESTRICT ON DELETE RESTRICT
);