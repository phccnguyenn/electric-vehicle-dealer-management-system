package com.evdealer.ev_dealer_management.sale.service;

import com.evdealer.ev_dealer_management.sale.repository.ProgramDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramDetailService {

    private final ProgramDetailRepository programDetailRepository;

}
