package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SuggestionService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<Doctor> suggestDoctors(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        City city;
        try {
            city = City.valueOf(patient.getCity().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new LocationNotCoveredException("We are still waiting to expand to your location");
        }

        Speciality speciality = getSpecialityBySymptom(patient.getSymptom());

        List<Doctor> doctors = doctorRepository.findByCityAndSpeciality(city, speciality);

        if (doctors.isEmpty()) {
            throw new NoDoctorsFoundException("There isn't any doctor present at your location for your symptom");
        }

        return doctors;
    }

    private Speciality getSpecialityBySymptom(Symptom symptom) {
        switch (symptom) {
            case ARTHRITIS:
            case BACK_PAIN:
            case TISSUE_INJURIES:
                return Speciality.ORTHOPAEDIC;
            case DYSMENORRHEA:
                return Speciality.GYNECOLOGY;
            case SKIN_INFECTION:
            case SKIN_BURN:
                return Speciality.DERMATOLOGY;
            case EAR_PAIN:
                return Speciality.ENT;
            default:
                throw new IllegalArgumentException("Unknown symptom");
        }
    }
}
