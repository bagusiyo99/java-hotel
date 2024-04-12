package com.hotel.dto;

import com.hotel.enums.ReservationStatus;
import com.hotel.enums.ReviewStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class ReservationDTO {


    private Long id;

    private Date bookDate;

    private String serviceName;

    private ReservationStatus reservationStatus;

    private ReviewStatus reviewStatus;

    private Long userId;

    private String userName;

    private Long companyId;

    private Long adId;

}
