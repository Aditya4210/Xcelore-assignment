package com.example.service;

import com.example.entity.Doctor;
import com.example.entity.Patient;
import com.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorService doctorService;

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Doctor> suggestDoctors(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        String speciality = mapSymptomToSpeciality(patient.getSymptom());
        return doctorService.suggestDoctors(patient.getCity(), speciality);
    }

    private String mapSymptomToSpeciality(String symptom) {
        switch (symptom) {
            case "Arthritis":
            case "Back Pain":
            case "Tissue injuries":
                return "Orthopaedic";
            case "Dysmenorrhea":
                return "Gynecology";
            case "Skin infection":
            case "Skin burn":
                return "Dermatology";
            case "Ear pain":
                return "ENT";
            default:
                throw new RuntimeException("Unknown symptom");
        }
    }
}
