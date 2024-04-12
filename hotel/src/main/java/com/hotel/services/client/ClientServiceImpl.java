// com.hotel.services.client.ClientServiceImpl

package com.hotel.services.client;

import com.hotel.dto.AdDTO;
import com.hotel.dto.AdDetailsForClientDto;
import com.hotel.dto.ReservationDTO;
import com.hotel.entity.Ad;
import com.hotel.entity.Reservation;
import com.hotel.entity.User;
import com.hotel.enums.ReservationStatus;
import com.hotel.enums.ReviewStatus;
import com.hotel.repository.AdRepository;
import com.hotel.repository.ReservationRepository;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<AdDTO> getAllAds() {
        return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDTO> searchAdByName (String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public boolean bookService (ReservationDTO reservationDTO){
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional < User> optionalUser = userRepository.findById(reservationDTO.getUserId());

        if (optionalAd.isPresent() && optionalUser.isPresent()){
            Reservation reservation = new Reservation();

            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());

            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public AdDetailsForClientDto getAdDetailsByAdId (Long adId){
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsForClientDto adDetailsForClientDto = new AdDetailsForClientDto();

        if (optionalAd.isPresent()){
            adDetailsForClientDto.setAdDTO(optionalAd.get().getAdDto());
        }
        return adDetailsForClientDto;

    }

    public List<ReservationDTO> getAllBookingsByUserId (Long userId){
        return reservationRepository.findAllByUserId(userId)
                .stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }



}
